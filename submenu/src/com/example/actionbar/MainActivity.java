package com.example.actionbar;

import android.annotation.SuppressLint;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MainActivity extends Activity  {

    private SubMenu mGoItem;
    private MenuItem mClearItem;
    
    private static final int GO_ITEM_ID = 1;
    private static final int CLEAR_ITEM_ID = 2;
    private static final int NONE = 3;
    CharSequence selected = "Light";
    Button hide ;
    RelativeLayout overlay;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
        
        
        hide = (Button) findViewById(R.id.hide);
        overlay =(RelativeLayout) findViewById(R.id.overlay);
        ActionBar ab = getActionBar();
        ab.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.bg_titlebar_tile));
        ab.setLogo(getResources().getDrawable(R.drawable.button_1));
        ab.setDisplayShowTitleEnabled(false);
        setProgressBarIndeterminateVisibility(true);
        hide.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				overlay.setVisibility(View.VISIBLE);
				hide.setVisibility(4);
				
			}
		});
    }

    
    @SuppressLint("InlinedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

    	
        mGoItem = menu.addSubMenu(selected);
        mGoItem.setIcon(R.drawable.ic_menu_sort);
        //SubMenu sub = menu.addSubMenu("Theme");
       
        //getMenuState(selected);
        
        
        mGoItem.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        mClearItem = menu.add("Map");
        mClearItem.setIcon(R.drawable.google_maps_icon_pressed).setShowAsAction(
                MenuItem.SHOW_AS_ACTION_ALWAYS);
        
        SubMenu subMenu2 = menu.addSubMenu("Map");
        MenuItem subMenu2Item = subMenu2.getItem();
        subMenu2Item.setIcon(R.drawable.ic_launcher);

        SubMenu subMenu3 = menu.addSubMenu("SortBY");
        MenuItem subMenu3Item = subMenu3.getItem();
        subMenu3Item.setIcon(R.drawable.ic_menu_sort);
        return super.onCreateOptionsMenu(menu);

        //return true;
    }
    
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	setProgressBarIndeterminateVisibility(false);
    	Toast.makeText(this, "Theme changed to \"" + item.getTitle() + "\"", Toast.LENGTH_SHORT).show();
    	if( item.getTitle().equals("Map")){
        	Toast.makeText(this, "Theme changed to \"" + item.getTitle() + "\"", Toast.LENGTH_SHORT).show();
        	Intent mapIntent = new Intent(this,Map.class);
        	startActivity(mapIntent);
        } else {
        	
        }
        return true;
    }    
      

	/*@Override
	public boolean onMenuItemClick(MenuItem item) {
		//Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
		mGoItem.clear();
		getMenuState(item.getTitle());
		return false;
	}*/
	
	
	/*public void getMenuState(CharSequence selected){
		
		 if(selected.equals("Default")) {
	        	mGoItem.add("Default").setIcon(android.R.drawable.checkbox_on_background).setOnMenuItemClickListener(this);
	        	mGoItem.add("Light").setIcon(android.R.drawable.checkbox_off_background).setOnMenuItemClickListener(this);
	        	mGoItem.add("Light (Dark Action Bar)").setIcon(android.R.drawable.checkbox_off_background).setOnMenuItemClickListener(this);
	        }else if(selected.equals("Light")) {
	        	mGoItem.add("Default").setIcon(android.R.drawable.checkbox_off_background).setOnMenuItemClickListener(this);
	        	mGoItem.add("Light").setIcon(android.R.drawable.checkbox_on_background).setOnMenuItemClickListener(this);
	        	mGoItem.add("Light (Dark Action Bar)").setIcon(android.R.drawable.checkbox_off_background).setOnMenuItemClickListener(this);
	        } else {
	        	mGoItem.add("Default").setIcon(android.R.drawable.checkbox_off_background).setOnMenuItemClickListener(this);
	        	mGoItem.add("Light").setIcon(android.R.drawable.checkbox_off_background).setOnMenuItemClickListener(this);
	        	mGoItem.add("Light (Dark Action Bar)").setIcon(android.R.drawable.checkbox_on_background).setOnMenuItemClickListener(this);
	        }
		 
	}*/
}