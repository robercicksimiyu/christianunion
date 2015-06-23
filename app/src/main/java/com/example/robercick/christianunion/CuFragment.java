package com.example.robercick.christianunion;

/**
 * Created by robercick on 5/11/15.
 */
/*public class CuFragment extends android.support.v4.app.Fragment {
    private ArrayAdapter<String> mCuAdapter;
    public CuFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        String[] menuArray={
                "Events",
                "News",
                "Ministries",
                "Media",
                "Get Involved",
                "About"
        };

        List<String> cuMenu=new ArrayList<>(Arrays.asList(menuArray));

        mCuAdapter = new ArrayAdapter<String>(
                //get the current context(This fragment parent activity)
                getActivity(),
                //id of the list item layout
                R.layout.list_item_cu,
                //Id of the text view to populate
                R.id.list_item_cu_textview,
                cuMenu);

        ListView listView=(ListView) rootView.findViewById(R.id.listview_cu);

        listView.setAdapter(mCuAdapter);


        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //This line is added for the fragment to handle menu events
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {                ;
        inflater.inflate(R.menu.cu_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handleaction bar item clicks here. The action bar will
        // automatically handle clicks on the home/up button, so long as
        //you specify the parent activity in AndroidManifest.xml
       int id=item.getItemId();
       if(id==R.id.action_refresh){
           FetchCuData cuTask=new FetchCuData();
           cuTask.execute("ministries");
           return true;
       }
        return super.onOptionsItemSelected(item);
    }




    *//*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =
                getMenuInflater();
        inflater.inflate(R.menu.cu_menu, menu);
        return true;
    }*//*





}*/
