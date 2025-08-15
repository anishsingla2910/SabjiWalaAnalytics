package com.prototypes.sabjiwalaanalytics;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.prototypes.sabjiwalaanalytics.add_category.AddCategoryActivity;
import com.prototypes.sabjiwalaanalytics.login.LoginActivity;
import com.prototypes.sabjiwalaanalytics.ui.command_fragment.CommandFragment;
import com.prototypes.sabjiwalaanalytics.ui.customers.CustomersFragment;
import com.prototypes.sabjiwalaanalytics.ui.master_list.MasterListFragment;
import com.prototypes.sabjiwalaanalytics.ui.sabji_wale.SabjiWalaFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	
	DrawerLayout drawer;
	FirebaseAuth fAuth;
	MasterListFragment masterListFragment;
	CustomersFragment customersFragment;
	SabjiWalaFragment shopsFragment;
	CommandFragment commandFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("Home");
		masterListFragment = new MasterListFragment();
		customersFragment = new CustomersFragment();
		shopsFragment = new SabjiWalaFragment();
		commandFragment = new CommandFragment();
		loadFragment(masterListFragment);
		fAuth = FirebaseAuth.getInstance();
		drawer = findViewById(R.id.drawer_layout);
		NavigationView navigationView = findViewById(R.id.nav_view);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		navigationView.setNavigationItemSelectedListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.nav_add_category){
			Intent intent = new Intent(MainActivity.this, AddCategoryActivity.class);
			startActivity(intent);
			finish();
		}else if (item.getItemId() == R.id.nav_master_list){
			loadFragment(masterListFragment);
		}else if(item.getItemId() == R.id.nav_customers){
			loadFragment(customersFragment);
		}else if (item.getItemId() == R.id.nav_log_out){{
			fAuth.signOut();
			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
			startActivity(intent);
			finish();
		}}else if(item.getItemId() == R.id.nav_shops){
			loadFragment(shopsFragment);
		}else if(item.getItemId() == R.id.nav_command_fragment){
			loadFragment(commandFragment);
		}
		/*switch (item.getItemId()){
			case R.id.nav_add_category: {
				Intent intent = new Intent(MainActivity.this, AddCategoryActivity.class);
				startActivity(intent);
				finish();
				break;
			}
			case R.id.nav_master_list: {
				loadFragment(masterListFragment);
				break;
			}
			case R.id.nav_customers: {
				loadFragment(customersFragment);
				break;
			}
			case R.id.nav_log_out: {
				fAuth.signOut();
				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
				break;
			}
			case R.id.nav_shops: {
				loadFragment(shopsFragment);
				break;
			}
			case R.id.nav_command_fragment: {
				loadFragment(commandFragment);
			}
		}*/
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
	
	public void setTitle(String title){
		getSupportActionBar().setTitle(title);
	}
	
	public void loadFragment(Fragment someFragment) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.flContent, someFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
}