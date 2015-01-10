package com.example.myrobots.utils;

import java.sql.Timestamp;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.myrobots.Messagee;

public class JsonUitls {
	public static Messagee executeJson(String json) {
		JSONObject jsonObject;
		Messagee msg = null;
		try {
			jsonObject = new JSONObject(json);
			String code = jsonObject.getString("code");
			String text = jsonObject.getString("text");
			msg = new Messagee(text, new Timestamp(System.currentTimeMillis()));
			return msg;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return msg;

	}
}
