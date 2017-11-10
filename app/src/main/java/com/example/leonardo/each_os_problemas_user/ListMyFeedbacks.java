package com.example.leonardo.each_os_problemas_user;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ListMyFeedbacks#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListMyFeedbacks extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NUSP = "nusp";
    String listJson;
    SimpleAdapter adapter;

    // TODO: Rename and change types of parameters
    private String nusp;

    public ListMyFeedbacks() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param nusp Parameter 1.
     * @return A new instance of fragment ListMyFeedbacks.
     */
    // TODO: Rename and change types and number of parameters
    public static ListMyFeedbacks newInstance(String nusp) {
        ListMyFeedbacks fragment = new ListMyFeedbacks();
        Bundle args = new Bundle();
        args.putString(ARG_NUSP, nusp);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nusp = getArguments().getString(ARG_NUSP);
        }
        AsyncTaskGetFeedbacksClass AsyncTaskGetFeedbacksObj = new AsyncTaskGetFeedbacksClass();
        AsyncTaskGetFeedbacksObj.execute();
    }

    class AsyncTaskGetFeedbacksClass extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String response) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            listJson = response;
            Log.d("json", listJson);
            loadList();
            Log.d("response",response);
        }

        @Override
        protected String doInBackground(Void... params) {
            UserFunctions uf = new UserFunctions();
            String response = uf.getUserFeedbacks(nusp);
            return response;
        }
    }

    public void loadList(){
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String,String>>();
        try {
            JSONArray feedbacksArray = new JSONArray(listJson);
            HashMap<String, String> map = new HashMap<String, String>();
            //FILL
            for(int i=0;i<feedbacksArray.length();i++)
            {
                map=new HashMap<String, String>();
                map.put("Description", feedbacksArray.getJSONObject(i).getString("description"));
                data.add(map);
            }
            //KEYS IN MAP
            String[] from={"Description"};
            //IDS OF VIEWS
            int[] to={R.id.txtDescription};
            //ADAPTER
            adapter=new SimpleAdapter(getActivity(), data, R.layout.fragment_list_my_problems, from, to);
            setListAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
