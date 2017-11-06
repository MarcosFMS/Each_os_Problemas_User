package com.example.leonardo.each_os_problemas_user;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.data;

public class ListMyProblems extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NUSP = "nusp";
    ListView list;
    String listJson;
    SimpleAdapter adapter;

    // TODO: Rename and change types of parameters
    private String nusp;

    public ListMyProblems() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param nusp Parameter 1.
     * @return A new instance of fragment ListMyProblems.
     */
    // TODO: Rename and change types and number of parameters
    public static ListMyProblems newInstance(String nusp) {
        ListMyProblems fragment = new ListMyProblems();
        Bundle args = new Bundle();
        args.putString(NUSP, nusp);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nusp = getArguments().getString(NUSP);
        }
        AsyncTaskGetProblemsClass AsyncTaskGetProblemsOBJ = new AsyncTaskGetProblemsClass();
        AsyncTaskGetProblemsOBJ.execute();
    }

    class AsyncTaskGetProblemsClass extends AsyncTask<Void, Void, String> {

        public AsyncTaskGetProblemsClass(){
        }

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
            String response = uf.getUserProblems(nusp);
            return response;
        }
    }

    public void loadList(){
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String,String>>();
        try {
            JSONArray problemsArray = new JSONArray(listJson);
            HashMap<String, String> map = new HashMap<String, String>();
            //FILL
            for(int i=0;i<problemsArray.length();i++)
            {
                map=new HashMap<String, String>();
                map.put("Description", problemsArray.getJSONObject(i).getString("description"));
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
