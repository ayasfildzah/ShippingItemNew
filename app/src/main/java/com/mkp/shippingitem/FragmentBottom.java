package com.mkp.shippingitem;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class FragmentBottom extends AppCompatActivity {
    private UserActivity homeFragment = new UserActivity();
    private AboutActivity aboutFragment = new AboutActivity();
    private AccountFragment accountFragment = new AccountFragment();

    private ImageView icMenuHome, icMenuTask, icMenuAccount;
    private TextView lblMenuHome, lblMenuTask, lblMenuAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_bottom);
        icMenuHome =findViewById(R.id.ic_menu_home);
        icMenuTask =findViewById(R.id.ic_menu_task);
        icMenuAccount =findViewById(R.id.ic_menu_account);

        lblMenuHome =findViewById(R.id.label_menu_home);
        lblMenuTask =findViewById(R.id.label_menu_task);
        lblMenuAccount =findViewById(R.id.label_menu_account);
        changeFragment(homeFragment, icMenuHome,lblMenuHome);
    }
    public void  menuNavigationClick(View view){
        switch (view.getId()){
            case R.id.menu_home:
                changeFragment(homeFragment, icMenuHome, lblMenuHome);
                break;
            case R.id.menu_task:
                changeFragment(aboutFragment, icMenuTask,lblMenuTask);
                break;
            case R.id.menu_account:
                changeFragment(accountFragment, icMenuAccount,lblMenuAccount);
                break;

            default:
                return;
        }
    }

    private void changeFragment(Fragment fragment, ImageView icon, TextView label){
        clearActiveMenu();
        icon.setImageTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary));
        label.setTextColor(ContextCompat.getColorStateList(this,R.color.colorPrimary));

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void clearActiveMenu() {
        icMenuHome.setImageTintList(ContextCompat.getColorStateList(this, R.color.colorInActive));
        lblMenuHome.setTextColor(ContextCompat.getColorStateList(this, R.color.colorInActive));
        icMenuTask.setImageTintList(ContextCompat.getColorStateList(this, R.color.colorInActive));
        lblMenuTask.setTextColor(ContextCompat.getColorStateList(this, R.color.colorInActive));
        icMenuAccount.setImageTintList(ContextCompat.getColorStateList(this, R.color.colorInActive));
        lblMenuAccount.setTextColor(ContextCompat.getColorStateList(this, R.color.colorInActive));

    }

}


