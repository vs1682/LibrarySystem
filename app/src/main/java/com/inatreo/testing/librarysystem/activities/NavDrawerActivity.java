package com.inatreo.testing.librarysystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.inatreo.testing.librarysystem.R;
import com.inatreo.testing.librarysystem.adapters.MemberListAdapter;
import com.inatreo.testing.librarysystem.database.CRUDBook;
import com.inatreo.testing.librarysystem.database.DBManager;
import com.inatreo.testing.librarysystem.utils.ExportImportDB;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.IOException;

/**
 * Created by vishal on 1/27/2016.
 */
public class NavDrawerActivity extends AppCompatActivity {

    protected Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_navdrawer, null);
        FrameLayout activityContainer = (FrameLayout) linearLayout.findViewById(R.id.flActivityContainer);
        super.setContentView(linearLayout);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        setDrawer();
    }

    private void setDrawer() {
        Toolbar tbGlobal = (Toolbar)findViewById(R.id.tbGlobal);
        setSupportActionBar(tbGlobal);
        drawer = new DrawerBuilder()
                .withToolbar(tbGlobal)
                .withActivity(this)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("USERS").withIdentifier(8),
                        new PrimaryDrawerItem().withName("BOOKS").withIdentifier(7),
                        new PrimaryDrawerItem().withName("EXPORT BACKUP").withIdentifier(1),
                        new PrimaryDrawerItem().withName("IMPORT BACKUP").withIdentifier(2),
                        new PrimaryDrawerItem().withName("CHECK IF IMPORTED").withIdentifier(3),
                        new PrimaryDrawerItem().withName("SEARCH").withIdentifier(4),
                        new PrimaryDrawerItem().withName("ADD MEMBER").withIdentifier(5),
                        new PrimaryDrawerItem().withName("ADD BOOK").withIdentifier(6),
                        new PrimaryDrawerItem().withName("LOG OUT")

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                                   @Override
                                                   public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                                       switch (drawerItem.getIdentifier()) {
                                                           case 1:
                                                               try {
                                                                   int i = ExportImportDB.exportDB();
                                                                   if (i == 1) {
                                                                       Toast.makeText(getBaseContext(), "export successful!!", Toast.LENGTH_SHORT).show();
                                                                   }else
                                                                       Toast.makeText(getBaseContext(), "export unsuccessful!!", Toast.LENGTH_SHORT).show();
                                                               } catch (IOException e) {
                                                                   e.printStackTrace();
                                                               }
                                                               break;
                                                           case 2:
                                                               if (ExportImportDB.isBackupPresent()) {
                                                                   Toast.makeText(getBaseContext(), "importing", Toast.LENGTH_SHORT).show();
                                                                   try {
                                                                       int i = ExportImportDB.importDB(getApplicationContext());
                                                                       if (i == 1)
                                                                           Toast.makeText(getBaseContext(), "import successful!!", Toast.LENGTH_SHORT).show();
                                                                       else
                                                                           Toast.makeText(getBaseContext(), "import unsuccessful!!", Toast.LENGTH_SHORT).show();
                                                                   } catch (IOException e) {
                                                                       e.printStackTrace();
                                                                   }
                                                               } else
                                                                   Toast.makeText(getBaseContext(), "Backup not present", Toast.LENGTH_SHORT).show();
                                                               break;
                                                           case 3:
                                                               if (ExportImportDB.isDbPresent())
                                                                   Toast.makeText(getBaseContext(), "present", Toast.LENGTH_SHORT).show();
                                                               else
                                                                   Toast.makeText(getBaseContext(), "not present", Toast.LENGTH_SHORT).show();
                                                               break;
                                                           case 4:
                                                               //CRUDBook.getInstance(getApplicationContext()).getBookDetails();
                                                               Intent intentHome = new Intent(NavDrawerActivity.this, HomePageActivity.class);
                                                               startActivity(intentHome);
                                                               break;
                                                           case 5:
                                                               Intent intent = new Intent(NavDrawerActivity.this, AddMemberActivity.class);
                                                               startActivity(intent);
                                                               break;
                                                           case 6:
                                                               Intent intentBook = new Intent(NavDrawerActivity.this, AddBookActivity.class);
                                                               startActivity(intentBook);
                                                               break;
                                                           case 7:
                                                               Intent intentBookList = new Intent(NavDrawerActivity.this, BookListActivity.class);
                                                               startActivity(intentBookList);
                                                               break;
                                                           case 8:
                                                               Intent intentMemberList = new Intent(NavDrawerActivity.this, MemberListActivity.class);
                                                               startActivity(intentMemberList);
                                                               break;
                                                       }
                                                       return false;
                                                   }
                                               }
                )
                .build();
    }
}
