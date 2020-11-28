package edu.temple.webbrowserapp;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TrashBinAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<String> Str;
    private AttentionClickListener lster;

    //interface

    public interface AttentionClickListener {
        void DeleteItem(int iID);
        void OnBookmartClick(int iID);
    }

    public TrashBinAdapter(Context context, ArrayList<String> text){
        this.context = context;
        this.Str = text;
    }

    public void setAttentionClickListener(AttentionClickListener attentionClickListener) {
        lster = attentionClickListener;
    }
    //public View getView
    //ViewHolder vhs = (ViewHolder) v.getTag();
    //vhs.textView.setText(getItem(position).toString());

    @Override
    public int getCount() {
        return Str.size();
    }

    @Override
    public Object getItem(int position) {
        return Str.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.bookmarkadapter, null);
        }


        final TextView txtTitle=(TextView)view.findViewById(R.id.WebURL);
        txtTitle.setText(Str.get(position));


        txtTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lster.OnBookmartClick(pos);
            }
        });

        final ImageView deletebutton = (ImageView)view.findViewById(R.id.DeleteButton);

        deletebutton.setTag(position);

        deletebutton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Str.remove(pos);
                lster.DeleteItem(pos);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}

