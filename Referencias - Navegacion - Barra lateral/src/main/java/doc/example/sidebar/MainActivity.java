package doc.example.sidebar;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class MainActivity extends Activity implements DrawerLayout.DrawerListener, ListView.OnItemClickListener {
    private String[] menuItems;
    private int[] images;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        initImages();
        configureDrawer();
        configureActionBar();
    }
//
    private void initImages() {
        images = new int[] {
                android.R.drawable.ic_menu_add,
                android.R.drawable.ic_menu_delete,
                android.R.drawable.ic_menu_gallery,
                android.R.drawable.ic_menu_info_details,
                android.R.drawable.ic_menu_close_clear_cancel,
                android.R.drawable.ic_menu_share,
                android.R.drawable.ic_menu_view
        };
    }

    private void configureActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    private void configureDrawer() {
        menuItems = getResources().getStringArray(R.array.menu_items);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerListener(this);

        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setOnItemClickListener(this);
        drawerList.setAdapter(new CustomDrawerAdapter(this,
                R.layout.drawer_list_item, menuItems, images));

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                android.R.drawable.ic_menu_more,
                R.string.drawer_open,
                R.string.drawer_closed);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override // DrawerListener
    public void onDrawerClosed(View drawerView) {
        getActionBar().setTitle("Sidebar - Closed");
    }


    @Override // DrawerListener
    public void onDrawerOpened(View drawerView) {
        getActionBar().setTitle("Sidrbar - Open");
    }


    @Override // DrawerListener
    public void onDrawerSlide(View drawerView, float slideOffset) {
    }

    @Override // DrawerListener
    public void onDrawerStateChanged(int i) {}


    @Override // OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, menuItems[i],Toast.LENGTH_SHORT).show();
        drawerLayout.closeDrawer(drawerList);
    }


    // List view adapter
    private class CustomDrawerAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] objects;
        private final int[] images;

        public CustomDrawerAdapter(Context context, int resource, String[] objects, int[] images) {
            super(context, resource, objects);
            this.context = context;
            this.objects = objects;
            this.images = images;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.drawer_list_item, null);

            TextView textView = (TextView) rowView.findViewById(R.id.planetTextView);
            textView.setText(objects[position]);

            ImageView image = (ImageView) rowView.findViewById(R.id.imageView);
            image.setImageResource(images[position]);
            return rowView;
        }
    }
}
