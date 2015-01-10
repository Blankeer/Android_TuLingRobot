package com.example.myrobots;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {

	private List<Messagee> list;
	private Context context;

	public MessageAdapter(Context context, List<Messagee> objects) {
		this.context = context;
		list = objects;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		ViewHolder vh = null;
		Messagee msg = list.get(position);
		if (convertView == null) {
			if (msg.isSend()) {
				view = LayoutInflater.from(context).inflate(
						R.layout.msg_item_me, null);
			} else {
				view = LayoutInflater.from(context).inflate(
						R.layout.msg_item_robots, null);
			}
			vh = new ViewHolder();
			vh.textViewTime = (TextView) view.findViewById(R.id.msg_time);
			vh.textViewContent = (TextView) view.findViewById(R.id.msg_text);
			vh.isSend = msg.isSend();
			view.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
			if (vh.isSend == msg.isSend()) {
				view = convertView;
			} else {
				if (msg.isSend()) {
					view = LayoutInflater.from(context).inflate(
							R.layout.msg_item_me, null);
				} else {
					view = LayoutInflater.from(context).inflate(
							R.layout.msg_item_robots, null);
				}
				vh = new ViewHolder();// 必须加这句，否则会混乱，我也不知道为啥
				vh.textViewTime = (TextView) view.findViewById(R.id.msg_time);
				vh.textViewContent = (TextView) view
						.findViewById(R.id.msg_text);
				vh.isSend = msg.isSend();
				view.setTag(vh);
			}
		}
		vh.textViewContent.setText(msg.getContent());
		vh.textViewTime.setText(msg.getTimeInfo());
		return view;
		/*
		 * Messagee msg = list.get(position); View view = null; if
		 * (msg.isSend()) { view =
		 * LayoutInflater.from(context).inflate(R.layout.msg_item_me, null); }
		 * else { view = LayoutInflater.from(context).inflate(
		 * R.layout.msg_item_robots, null); } TextView tv = (TextView)
		 * view.findViewById(R.id.msg_text); TextView time = (TextView)
		 * view.findViewById(R.id.msg_time); tv.setText(msg.getContent());
		 * time.setText(msg.getTimeInfo()); return view;
		 */
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		public TextView textViewTime, textViewContent;
		public boolean isSend;
	}
}
