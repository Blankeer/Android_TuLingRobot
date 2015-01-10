package com.example.myrobots;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.myrobots.utils.JsonUitls;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.ViewInjectInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
	private final String tulinUrl = "http://www.tuling123.com/openapi/api",
			tulingKey = "71f28bf79c820df10d39b4074345ef8c";
	private Button buttonSend;
	private EditText editText;
	private ListView listView;
	private List<Messagee> list;
	private MessageAdapter adapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		viewInit();
		dataInit();
		listViewInit();
	}

	// 点击发送按钮的事件，尝试用框架注解的方式写
	@OnClick(R.id.main_button)
	public void buttonSend(View v) {
		String content = this.editText.getText().toString().trim();
		if (!content.equals("")) {
			Messagee msg = new Messagee(content, new Timestamp(
					System.currentTimeMillis()), true);
			this.list.add(msg);
			long c = msg.getTime().getTime()
					- list.get(list.size() == 1 ? 0 : list.size() - 2)
							.getTime().getTime();// 得到上一个消息，获得时间差
			String t = "";
			if (c > 1000 * 60) {// 发送的这条消息和上一条相隔 这个间隔 ，显示发送的时间 毫秒
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy年MM月dd日 hh:mm:ss");
				t = sdf.format(msg.getTime());
			}
			msg.setTimeInfo(t);
			this.adapter.notifyDataSetChanged();
			sendMessage(content);
			this.editText.setText("");
			if (this.list.size() > 30) {
				for (int i = 0; i <= list.size() - 30; i++) {
					list.remove(i);
				}
			}
		}

	}

	private void sendMessage(String sendMsg) {
		com.lidroid.xutils.HttpUtils httpUtils = new com.lidroid.xutils.HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("key", tulingKey);
		params.addBodyParameter("info", sendMsg);
		httpUtils.configTimeout(5000);
		httpUtils.send(HttpMethod.POST, tulinUrl, params,
				new RequestCallBack<String>() {
					Messagee msg = null;

					public void onFailure(HttpException arg0, String arg1) {
						msg = new Messagee(getResources().getString(
								R.string.error_info)
								+ "\n详细报错如下:" + arg1, new Timestamp(System
								.currentTimeMillis()));
						list.add(msg);
						adapter.notifyDataSetChanged();
					}

					public void onSuccess(ResponseInfo<String> re) {
						msg = JsonUitls.executeJson(re.result);
						list.add(msg);
						adapter.notifyDataSetChanged();
					}
				});
	}

	private void dataInit() {// 欢迎语
		list = new ArrayList<Messagee>();
		String welcome = "主人，在此恭候多时了。。。", t;
		Messagee msg = new Messagee(welcome, new Timestamp(
				System.currentTimeMillis()), false);
		this.list.add(msg);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
		t = sdf.format(msg.getTime());
		msg.setTimeInfo(t);
	}

	private void listViewInit() {
		adapter = new MessageAdapter(this, list);
		listView.setAdapter(adapter);
	}

	private void viewInit() {
		buttonSend = (Button) findViewById(R.id.main_button);
		editText = (EditText) findViewById(R.id.main_edit);
		listView = (ListView) findViewById(R.id.main_listview);
	}
}
