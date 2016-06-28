package com.acuo.collateral.resources.view;

import com.acuo.collateral.model.Direction;
import com.acuo.collateral.model.ProductType;
import com.acuo.collateral.model.Status;

import java.util.Date;
import java.util.function.Function;

public class Exposure {

    private String positionId;
    private String note;
    private ProductType productType;
    private Date tradeDate;
    private Date effectiveDate;
    private Date maturityDate;
    private Date clearingDate;
    private Direction direction;
    private Status status;
    private String source;
    private Counterpart counterpart;

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Date getClearingDate() {
        return clearingDate;
    }

    public void setClearingDate(Date clearingDate) {
        this.clearingDate = clearingDate;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Counterpart getCounterpart() {
        return counterpart;
    }

    public void setCounterpart(Counterpart counterpart) {
        this.counterpart = counterpart;
    }

    public static Function<com.acuo.collateral.model.Exposure, Exposure> getCreate() {
        return create;
    }

    public static void setCreate(Function<com.acuo.collateral.model.Exposure, Exposure> create) {
        Exposure.create = create;
    }

    public static Function<com.acuo.collateral.model.Exposure, Exposure> getCreateDetailed() {
        return createDetailed;
    }

    public static void setCreateDetailed(Function<com.acuo.collateral.model.Exposure, Exposure> createDetailed) {
        Exposure.createDetailed = createDetailed;
    }

    public static Function<com.acuo.collateral.model.Exposure, Exposure> create = t -> {
        Exposure exposure = new Exposure();
        exposure.positionId = t.getPositionId();
        exposure.note = t.getNote();
        exposure.productType = t.getProductType();
        exposure.tradeDate = t.getTradeDate();
        exposure.effectiveDate = t.getEffectiveDate();
        exposure.maturityDate = t.getMaturityDate();
        exposure.clearingDate = t.getClearingDate();
        exposure.direction = t.getDirection();
        exposure.status = t.getStatus();
        exposure.source = t.getSource();

        return exposure;
    };

    public static Function<com.acuo.collateral.model.Exposure, Exposure> createDetailed = t -> {
        Exposure exposure = Exposure.create.apply(t);
        exposure.counterpart = Counterpart.create.apply(t.getCounterpart());

        return exposure;
    };

}