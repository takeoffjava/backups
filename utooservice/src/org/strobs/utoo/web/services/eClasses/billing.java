package org.strobs.utoo.web.services.eClasses;

public class billing 
{
	private int nCarType;
	private float fBaseMinimumKM; // car models
	private float fBaseFare; // car models
	private float fAfterMinmumKM; // car models
	private float fRideChargePerMin; //car models
	private float fPeakTimeAmtPerRide; 
	private float fTotalKMs;// invoice (req json)
	private float nTotalMins; // invoice (req json)
	private boolean isPeakTime; //tariff
	private boolean isInPromoCode; //tariff
	private float fPromoCodeAMT; //Promocode
	private boolean isTotalAMTPromocode;
	
	public billing(){}
	
	
	public billing(int nCarType, float fBaseMinimumKM, float fBaseFare,
			float fAfterMinmumKM, float fRideChargePerMin,
			float fPeakTimeAmtPerRide, float fTotalKMs, int nTotalMins,
			boolean isPeakTime, float fPromoCodeAMT) {
		super();
		this.nCarType = nCarType;
		this.fBaseMinimumKM = fBaseMinimumKM;
		this.fBaseFare = fBaseFare;
		this.fAfterMinmumKM = fAfterMinmumKM;
		this.fRideChargePerMin = fRideChargePerMin;
		this.fPeakTimeAmtPerRide = fPeakTimeAmtPerRide;
		this.fTotalKMs = fTotalKMs;
		this.nTotalMins = nTotalMins;
		this.isPeakTime = isPeakTime;
		this.fPromoCodeAMT = fPromoCodeAMT;
	}
	public int getnCarType() {
		return nCarType;
	}
	public void setnCarType(int nCarType) {
		this.nCarType = nCarType;
	}
	public float getfBaseMinimumKM() {
		return fBaseMinimumKM;
	}
	public void setfBaseMinimumKM(float fBaseMinimumKM) {
		this.fBaseMinimumKM = fBaseMinimumKM;
	}
	public float getfBaseFare() {
		return fBaseFare;
	}
	public void setfBaseFare(float fBaseFare) {
		this.fBaseFare = fBaseFare;
	}
	public float getfAfterMinmumKM() {
		return fAfterMinmumKM;
	}
	public void setfAfterMinmumKM(float fAfterMinmumKM) {
		this.fAfterMinmumKM = fAfterMinmumKM;
	}
	public float getfRideChargePerMin() {
		return fRideChargePerMin;
	}
	public void setfRideChargePerMin(float fRideChargePerMin) {
		this.fRideChargePerMin = fRideChargePerMin;
	}
	public float getfPeakTimeAmtPerRide() {
		return fPeakTimeAmtPerRide;
	}
	public void setfPeakTimeAmtPerRide(float fPeakTimeAmtPerRide) {
		this.fPeakTimeAmtPerRide = fPeakTimeAmtPerRide;
	}
	public float getfTotalKMs() {
		return fTotalKMs;
	}
	public void setfTotalKMs(float fTotalKMs) {
		this.fTotalKMs = fTotalKMs;
	}
	
	
	public float getnTotalMins() {
		return nTotalMins;
	}


	public void setnTotalMins(float nTotalMins) {
		this.nTotalMins = nTotalMins;
	}


	public boolean isPeakTime() {
		return isPeakTime;
	}
	public void setPeakTime(boolean isPeakTime) {
		this.isPeakTime = isPeakTime;
	}
	public float getfPromoCodeAMT() {
		return fPromoCodeAMT;
	}
	public void setfPromoCodeAMT(float fPromoCodeAMT) {
		this.fPromoCodeAMT = fPromoCodeAMT;
	}


	public boolean isTotalAMTPromocode() {
		return isTotalAMTPromocode;
	}


	public void setTotalAMTPromocode(boolean isTotalAMTPromocode) {
		this.isTotalAMTPromocode = isTotalAMTPromocode;
	}


	public boolean isInPromoCode() {
		return isInPromoCode;
	}


	public void setInPromoCode(boolean isInPromoCode) {
		this.isInPromoCode = isInPromoCode;
	}
	
}
