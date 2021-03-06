package pl.zankowski.iextrading4j.hist.api.message.administrative.builder;

import pl.zankowski.iextrading4j.hist.api.IEXMessageType;
import pl.zankowski.iextrading4j.hist.api.field.IEXPrice;
import pl.zankowski.iextrading4j.hist.api.message.administrative.IEXSecurityDirectoryMessage;
import pl.zankowski.iextrading4j.hist.api.message.administrative.field.IEXLULDTier;
import pl.zankowski.iextrading4j.hist.api.message.builder.TestDataBuilder;
import pl.zankowski.iextrading4j.hist.api.util.IEXByteTestUtil;

public class IEXSecurityDirectoryMessageDataBuilder implements TestDataBuilder {

    private byte flag = (byte) -112;
    private long timestamp = 1494855059287436131L;
    private String symbol = "SNAP";
    private int roundLotSize = 10;
    private IEXPrice adjustedPOCPrice = new IEXPrice(1234L);
    private IEXLULDTier luldTier = IEXLULDTier.TIER_1_NMS;

    public static IEXSecurityDirectoryMessage defaultDirectoryMessage() {
        return directoryMessage().build();
    }

    public static IEXSecurityDirectoryMessageDataBuilder directoryMessage() {
        return new IEXSecurityDirectoryMessageDataBuilder();
    }

    public IEXSecurityDirectoryMessageDataBuilder withFlag(final byte flag) {
        this.flag = flag;
        return this;
    }

    public IEXSecurityDirectoryMessageDataBuilder withTimestamp(final long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public IEXSecurityDirectoryMessageDataBuilder withSymbol(final String symbol) {
        this.symbol = symbol;
        return this;
    }

    public IEXSecurityDirectoryMessageDataBuilder withRoundLotSize(final int roundLotSize) {
        this.roundLotSize = roundLotSize;
        return this;
    }

    public IEXSecurityDirectoryMessageDataBuilder withAdjustedPOCPrice(final IEXPrice adjustedPOCPrice) {
        this.adjustedPOCPrice = adjustedPOCPrice;
        return this;
    }

    public IEXSecurityDirectoryMessageDataBuilder withLuldTier(final IEXLULDTier luldTier) {
        this.luldTier = luldTier;
        return this;
    }

    @Override
    public byte[] getBytes() {
        return IEXByteTestUtil.prepareBytes(IEXSecurityDirectoryMessage.LENGTH, IEXMessageType.SECURITY_DIRECTORY,
                flag, timestamp, symbol, roundLotSize, adjustedPOCPrice, luldTier);
    }

    public IEXSecurityDirectoryMessage build() {
        return IEXSecurityDirectoryMessage.createIEXMessage(getBytes());
    }

}
