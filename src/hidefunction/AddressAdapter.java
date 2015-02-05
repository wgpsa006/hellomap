package hidefunction;

import java.util.List;

import com.example.hellomap.R;
import com.example.hellomap.R.id;
import com.example.hellomap.R.layout;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AddressAdapter extends ArrayAdapter<AddressItemRow> {

	// 畫面資源編號

	// 包裝的記事資料
	private List<AddressItemRow> addresses;
	private Context context;

	public AddressAdapter(Context context_, List<AddressItemRow> items) {
		super(context_, R.layout.address_row, items);

		this.addresses = items;
		context = context_;
	}
	
	// 新增框架 step1
	public class ViewHolderItem {
		TextView addressTextView;
		TextView latitudeTextView;
		TextView longitudeTextView;
		TextView price_per_square_metersTextView;
		TextView product_typeTextView;
		TextView priceTextView;
		TextView areaTextView;
		TextView urlTextView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 先放到記憶體中
		ViewHolderItem viewHolder;
		
		// 表示第一次讀取
		if (convertView == null) {
			// inflate the layout
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			// put layout 
			convertView = inflater.inflate(R.layout.address_row, parent, false);
			
			viewHolder = new ViewHolderItem();
			convertView.setTag(viewHolder);
			
			//step 2
			
			viewHolder.addressTextView=(TextView)convertView.findViewById(R.id.row_address);
			viewHolder.latitudeTextView=(TextView)convertView.findViewById(R.id.row_latitude);
			viewHolder.longitudeTextView=(TextView)convertView.findViewById(R.id.row_longitude);
			viewHolder.price_per_square_metersTextView=(TextView)convertView.findViewById(R.id.row_price_per_square_meters);
			viewHolder.product_typeTextView=(TextView)convertView.findViewById(R.id.row_product_type);
			viewHolder.priceTextView=(TextView)convertView.findViewById(R.id.row_price);
			viewHolder.areaTextView=(TextView)convertView.findViewById(R.id.row_area);
			viewHolder.urlTextView=(TextView)convertView.findViewById(R.id.row_url);
			
			
		} else {
			viewHolder = (ViewHolderItem) convertView.getTag();
		}

		AddressItemRow addressItemRow = get(position);
		
		//step 3
		viewHolder.addressTextView.setText(addressItemRow.address);
		viewHolder.latitudeTextView.setText(addressItemRow.latitude);
		viewHolder.longitudeTextView.setText(addressItemRow.longitude);
		viewHolder.price_per_square_metersTextView.setText(addressItemRow.price_per_square_meters);
		viewHolder.product_typeTextView.setText(addressItemRow.product_type);
		viewHolder.priceTextView.setText(addressItemRow.price);
		viewHolder.areaTextView.setText(addressItemRow.area);
		viewHolder.urlTextView.setText(addressItemRow.url);
		
		return convertView;

	}

	// 設定指定編號的記事資料
	public void set(int index, AddressItemRow item) {
		if (index >= 0 && index < addresses.size()) {
			addresses.set(index, item);
			notifyDataSetChanged();
		}
	}

	// 讀取指定編號的記事資料
	public AddressItemRow get(int index) {
		return addresses.get(index);
	}

}
