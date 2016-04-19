package com.cml.imitate.netease.modules.home;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.cml.second.app.common.widget.menu.NavigationMenuView;

/**
 * Created by cmlBeliever on 2016/2/24.
 */
public class MenuHelper implements NavigationMenuView.OnMenuSelectedLisener {
    private MainActivity mainActivity;
    private DrawerLayout drawer;

    public MenuHelper(MainActivity mainActivity, DrawerLayout drawer) {
        this.mainActivity = mainActivity;
        this.drawer = drawer;
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        item.setChecked(false);
        item.setNumericShortcut('5');

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//            showContainer(MainFragment.class);
//        } else if (id == R.id.nav_gallery) {
//            ContainerActivity.startActivity(menuActivity, ShareFragment.class);
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
        drawer.closeDrawer(GravityCompat.START);
        mainActivity.setCustomTitle(item.getTitle());
        return true;
    }

    @Override
    public void onMenuSelected(ListView menuView, NavigationMenuView.MenuItem item, int index) {

//        switch (index) {
//            case 0:
//            case 1:
//                showContainer(MainFragment.class);
//                break;
//            case 2:
//                ContainerActivity.startActivity(menuActivity, ShareFragment.class);
//                break;
//            case 3:
//                ContainerActivity.startActivity(menuActivity, IndexableFragment.class);
//                break;
//            case 4:
//                ContainerActivity.startActivity(menuActivity, RecycerviewFragment.class);
//                break;
//            case 5:
//                ContainerActivity.startActivity(menuActivity, CoordinatorLayoutFragment.class);
//                break;
//            case 6:
//                ContainerActivity.startActivity(menuActivity, TextSwitcherFragment.class);
//                break;
//            case 7:
//                menuActivity.startActivity(new Intent(menuActivity, BottomSheetActivity.class));
////                ContainerActivity.startActivity(menuActivity, BottomSheetFragment.class);
//                break;
//        }
        drawer.closeDrawer(GravityCompat.START);
        if (null != item) {
            Toast.makeText(mainActivity, "点击：" + mainActivity.getString(item.menuText), Toast.LENGTH_LONG).show();
        }

    }
}
