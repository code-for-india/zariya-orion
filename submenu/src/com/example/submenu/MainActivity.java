
package com.example.submenu;


import android.graphics.Color;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;



import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

public class MainActivity extends SherlockActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        SubMenu subMenu1 = menu.addSubMenu("Action Item");
        subMenu1.add("How This Works");
        subMenu1.add("About Us");
        subMenu1.add("Contact Us");

        MenuItem subMenu1Item = subMenu1.getItem();
        subMenu1Item.setIcon(R.drawable.ic_title_share_default);
        subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

//        SubMenu subMenu2 = menu.addSubMenu("Overflow Item");
//        subMenu2.add("These");
//        subMenu2.add("Are");
//        subMenu2.add("Sample");
//        subMenu2.add("Items");
//
//        MenuItem subMenu2Item = subMenu2.getItem();
//        subMenu2Item.setIcon(R.drawable.ic_compose);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(SampleList.THEME); //Used for theme switching in samples
    	setTheme(R.style.Theme_Sherlock);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00853c")));
        //((TextView)findViewById(R.id.text)).setText(R.string.submenus_content);
    }
}
