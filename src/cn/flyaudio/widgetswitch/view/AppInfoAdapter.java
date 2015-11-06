package cn.flyaudio.widgetswitch.view;

import java.util.List;

import cn.flyaudio.widgetswitch.alltools.SkinResource;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
//import cn.flyaudio.widgetswitch.R;

public class AppInfoAdapter extends BaseAdapter {

	private LayoutInflater infater = null;
	private List<Picture> pictures;

	public AppInfoAdapter(Context context, List<Picture> picturess, int[] imgs) {
		super();
		infater = infater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		pictures = picturess;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pictures.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup rootView) {
		View view = null;
		ViewHolder holder = null;
		if (convertview == null || convertview.getTag() == null) {
			
			view = SkinResource.getSkinLayoutViewByName("app_select_item_layout");
			holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			view = convertview;
			holder = (ViewHolder) convertview.getTag();
		}

		holder.mAppIcon.setImageResource(pictures.get(position).getImageId());
		holder.mAppLable.setText(pictures.get(position).getTitlestring());
		return view;
	}

	static class ViewHolder {
		TextView mAppLable;
		ImageView mAppIcon;

		public ViewHolder(View view) {
/*			this.mAppLable = (TextView) view.findViewById(R.id.lable);
			this.mAppIcon = (ImageView) view.findViewById(R.id.icon);*/
			this.mAppLable = (TextView) view.findViewById(SkinResource.getSkinResourceId("lable", "id"));
			this.mAppIcon = (ImageView) view.findViewById(SkinResource.getSkinResourceId("icon", "id"));
		}
	}

}
