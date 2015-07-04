package uta.com.academics;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.GridView;

import uta.com.studentcenter.R;

/**
 * Created by vivekraveendran on 7/3/2015.
 */
public class AcademicsHome extends Activity{


    GridView grid;
    String[] gridItems = {
            "Search",
            "Add to cart",
            "Enroll",
            "Current Schedule",
            "Drop",
            "Swap"
    } ;
    int[] imageId = {
            R.drawable.search,
            R.drawable.addtocart,
            R.drawable.enroll,
            R.drawable.schedule,
            R.drawable.drop,
            R.drawable.swap
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.academics_home);
        grid = (GridView) findViewById(R.id.academics_home_grid);
        grid.setAdapter(new CustomGrid(this,gridItems, imageId));
    }


    public class CustomGrid extends BaseAdapter{
        private Context mContext;
        private final String[] web;
        private final int[] Imageid;

        public CustomGrid(Context c,String[] web,int[] Imageid ) {
            mContext = c;
            this.Imageid = Imageid;
            this.web = web;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return web.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View grid;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                grid = new View(mContext);
                grid = inflater.inflate(R.layout.grid_item, null);
                TextView textView = (TextView) grid.findViewById(R.id.grid_text);
                ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
                textView.setText(web[position]);
                imageView.setImageResource(Imageid[position]);
            } else {
                grid = (View) convertView;
            }

            return grid;
        }
    }
}
