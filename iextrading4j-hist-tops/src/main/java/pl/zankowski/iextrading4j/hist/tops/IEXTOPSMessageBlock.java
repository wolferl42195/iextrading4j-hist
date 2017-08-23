package pl.zankowski.iextrading4j.hist.tops;

import pl.zankowski.iextrading4j.hist.api.IEXMessageType;
import pl.zankowski.iextrading4j.hist.api.message.IEXMessage;
import pl.zankowski.iextrading4j.hist.api.message.IEXMessageHeader;
import pl.zankowski.iextrading4j.hist.api.message.IEXSegment;
import pl.zankowski.iextrading4j.hist.api.util.IEXByteConverter;
import pl.zankowski.iextrading4j.hist.tops.message.IEXQuoteUpdateMessage;
import pl.zankowski.iextrading4j.hist.tops.message.IEXTradeMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public class IEXTOPSMessageBlock extends IEXSegment {

    public IEXTOPSMessageBlock(IEXMessageHeader messageHeader, List<IEXMessage> messages) {
        super(messageHeader, messages);
    }

    public static IEXSegment createIEXSegment(byte[] packet) {
        List<IEXMessage> iexMessages = new ArrayList<>();
        int offset = 40;

        IEXMessageHeader iexMessageHeader = IEXMessageHeader.createIEXMessageHeader(Arrays.copyOfRange(packet, 0, offset));

        for (int i = 0; i < iexMessageHeader.getMessageCount(); i++) {
            short length = IEXByteConverter.convertBytesToShort(Arrays.copyOfRange(packet, offset, offset = offset + 2));
            iexMessages.add(resolveMessage(Arrays.copyOfRange(packet, offset, offset = offset + length)));
        }

        return new IEXTOPSMessageBlock(iexMessageHeader, iexMessages);
    }


    private static IEXMessage resolveMessage(byte[] bytes) {
        if (bytes.length != 42) {
            throw new IllegalArgumentException("IEX TOPS Message has to contain 42 bytes");
        }

        IEXMessageType messageType = IEXMessageType.getMessageType((char) bytes[0]);

        switch (messageType) {
            case QUOTE_UPDATE:
                return IEXQuoteUpdateMessage.createIEXMessage(messageType, bytes);
            case TRADE_REPORT:
                return IEXTradeMessage.createIEXMessage(messageType, bytes);
            case TRADE_BREAK:
                return IEXTradeMessage.createIEXMessage(messageType, bytes);
            default:
                return new IEXMessage();
        }
    }

}