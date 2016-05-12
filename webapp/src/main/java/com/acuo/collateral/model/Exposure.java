package com.acuo.collateral.model;

import java.util.Date;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.typeconversion.DateString;
import org.neo4j.ogm.annotation.typeconversion.EnumString;

@NodeEntity
public class Exposure extends Entity {

	private String positionId;

	private String note;

	@EnumString(value = ProductType.class)
	private ProductType productType;

	@DateString(value = "yyyy-MM-dd")
	private Date tradeDate;

	@DateString(value = "yyyy-MM-dd")
	private Date effectiveDate;

	@DateString(value = "yyyy-MM-dd")
	private Date maturityDate;

	@DateString(value = "yyyy-MM-dd")
	private Date clearingDate;

	@EnumString(value = Direction.class)
	private Direction direction;

	@EnumString(value = Status.class)
	private Status status;

	private String source;

	public String getPositionId() {
		return positionId;
	}

	public String getNote() {
		return note;
	}

	public ProductType getProductType() {
		return productType;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public Date getClearingDate() {
		return clearingDate;
	}

	public Direction getDirection() {
		return direction;
	}

	public Status getStatus() {
		return status;
	}

	public String getSource() {
		return source;
	}
}
