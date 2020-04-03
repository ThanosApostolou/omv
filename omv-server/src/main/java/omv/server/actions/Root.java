package omv.server.actions;

import com.google.gson.Gson;

public class Root {
	private String start_datetime;

	public Root(String start_datetime) {
		this.start_datetime = start_datetime;
	}

	public String getStartdatetime() {
		return this.start_datetime;
	}
	public void setStartdatetime(String start_datetime) {
		this.start_datetime = start_datetime;
	}

	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	public static Root fromString(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, Root.class);
	}

}