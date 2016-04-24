package com.uws.radd.data;

public class QueuedDrink {
	public final static int DRINK_QUEUED = 0;
	public final static int DRINK_MIXED = 1;
	public final static int DRINK_CANCELED = 2;

	private int queueId;
	private int userId;
	private String drinkName;
	private String cupSize;
	private int status;
	
	public QueuedDrink(int queueId, int userId, String drinkName, String cupSize, int status){
		this.queueId=queueId;
		this.userId=userId;
		this.drinkName=drinkName;
		this.cupSize=cupSize;
		this.status=status;
	}
	
	public int getQueueId() {
		return queueId;
	}

	public int getUserId() {
		return userId;
	}

	public String getDrinkName() {
		return drinkName;
	}

	public String getCupSize() {
		return cupSize;
	}

	public int getStatus() {
		return status;
	}
}
