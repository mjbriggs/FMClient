package michaelbriggs.familymapclient;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new MapFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        Drawable searchIcon = new IconDrawable(this, FontAwesomeIcons.fa_search).
                colorRes(R.color.colorPrimaryDark).sizeDp(24);
        menu.findItem(R.id.search_bar).setIcon(searchIcon);

        Drawable filterIcon = new IconDrawable(this, FontAwesomeIcons.fa_filter).
                colorRes(R.color.colorPrimaryDark).sizeDp(24);
        menu.findItem(R.id.filter_icon).setIcon(filterIcon);

        Drawable settingsIcon = new IconDrawable(this, FontAwesomeIcons.fa_cog).
                colorRes(R.color.colorPrimaryDark).sizeDp(24);
        menu.findItem(R.id.settings_icon).setIcon(settingsIcon);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()){
            case R.id.search_bar:
                intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.settings_icon:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.filter_icon:
                intent = new Intent(this, FilterActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
