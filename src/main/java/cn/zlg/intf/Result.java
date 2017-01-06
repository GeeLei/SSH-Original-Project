package cn.zlg.intf;

public class Result {

	public static final int Integer = 0;
	public static final int String = 1;
	public static final int JsonObject = 2;
	public static final int JsonArray = 3;

	private int statusCode = -1;
	private int valid = 0;
	private int dataType = JsonObject;
	private String msg;
	private Object data;
	

	/* private T dataObj; */

	public int getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/*
	 * public T getDataObj() { return dataObj; } public void setDataObj(T
	 * dataObj) { this.dataObj = dataObj; }
	 */

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "Result [statusCode=" + statusCode + ", valid=" + valid
				+ ", dataType=" + dataType + ", data=" + data + "]";
	}
	
}
