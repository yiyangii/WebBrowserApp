package edu.temple.webbrowserapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class PageListFragment extends Fragment {
    public void addSelectListener(OnItemSelectedListener listener){
        this.listener = listener;
    }

    public interface OnItemSelectedListener{
        void onItemSelected(int iID);
    }

    private OnItemSelectedListener listener;

    private ArrayList<String> array;
    private ListView ls;
    private ArrayAdapter listadapter;

    public PageListFragment() {

    }

    public static PageListFragment newInstance(ArrayList<String> lstWebTitle) {
        PageListFragment fragment = new PageListFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("WebTitle",lstWebTitle);
        fragment.setArguments(args);
        return fragment;
    }
    /*@Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        outState.putParcelableArrayList("ArrayList",fragments2);
        super.onSaveInstanceState(outState);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(pageTitles);
        dest.writeTypedList(fragments2);
        dest.writeInt(position);
        dest.writeTypedList(pages);
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View myFragmentView =inflater.inflate(R.layout.listviewactivity, container, false);
        ls =(ListView) myFragmentView.findViewById(R.id.List);
        array =new ArrayList<>();
        ArrayList<String> temp;
        if (getArguments()!=null) {
            temp = getArguments().getStringArrayList("WebTitle");

            for (int i=0;i<temp.size();i++){
                if (array.size()<=i){
                    array.add(temp.get(i));
                }
                else{
                    array.set(i,temp.get(i));
                }
            }
        }
        if (array ==null) {
            array = new ArrayList<>();
            array.add("");
        }
        listadapter =new ArrayAdapter<>( getActivity(), android.R.layout.simple_list_item_1, array);
        ls.setAdapter(listadapter);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener!=null){listener.onItemSelected(position);}
            }
        });

        return  myFragmentView;
    }
    //public void UpdateList(ArrayList<String> arrWebTitle){
    //for (int i=0;i<arrWebTitle.size();i++){
    //if (array.size()<=i){
    //array.add(arrWebTitle.get(i));
    //}
    // else{

    public void UpdateList(ArrayList<String> arrWebTitle){
        for (int i=0;i<arrWebTitle.size();i++){
            if (array.size()<=i){
                array.add(arrWebTitle.get(i));
            }
            else{
                array.set(i,arrWebTitle.get(i));
            }
        }
        listadapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
    }
}