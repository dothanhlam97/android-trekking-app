package com.app.trekking;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnInfoWindowClickListener{
        private static final String GOOGLE_API_KEY = "AIzaSyAfAGWP3hSqruk1pWEaCnLd_VT-wmLHHvU";
        private GoogleMap map;
        private String namePlace = "";
        private FloatingActionButton fab, fabTimDuong, fabTraCuu, fabToaDo;
        private  Animation moveCrossover, moveTop, moveLeft, controlFabForward, controlFabBackward;
        private boolean checkShowHide = true;
        private ArrayList<ReadJson> arrayReadJS=new ArrayList<ReadJson>();
        private ArrayList<ReadJson> arrayReadJSAutoComplete =new ArrayList<ReadJson>();
        private ArrayList<LoadImage> arrayLoadIM=new ArrayList<LoadImage>();
        private int mode = 0; //default=drive
        private int type = 0; //Normal
        private int theme = 0; //Light
        private Dialog findDialog, typeDialog,themeDialog,oDialog;
        private EditText from, to;
        private static int editPlace = -1;
        private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
        private List<Marker> originMarker = new ArrayList<>();
        private List<Marker> destinationMarker = new ArrayList<>();
        private List<Polyline> polylinePaths = new ArrayList<>();
        private ProgressDialog progressDialog;
        private EditText searchBar;
        private ImageView imgLogo;
        Bitmap smallHuman;
        Bitmap smallCar;
        Bitmap smallMarker;
        private TextView txtUsername;
        private TextView txtEmail;
        private String UserName = "";
        private String Email = "";
        String profilePicUrl = "";
        public static boolean isLogin = false;
        public static String nameLocation = "";

        String Gender = "";
        String Dob = "";

        private SupportMapFragment mapFragment;

        public Bitmap makeSmallIcon(int id) {
            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(id);
            Bitmap b=bitmapdraw.getBitmap();
            return Bitmap.createScaledBitmap(b, 80, 80, false);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // create map
            mapFragment =
                    (SupportMapFragment)getSupportFragmentManager()
                            .findFragmentById(R.id.MyMap);
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }

            //Làm icon nhỏ hơn
            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.human);
            Bitmap b=bitmapdraw.getBitmap();
            smallHuman = Bitmap.createScaledBitmap(b, 80, 80, false);
            bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.car);
            b=bitmapdraw.getBitmap();
            smallCar = Bitmap.createScaledBitmap(b, 80, 80, false);
            bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.mapmarker);
            b=bitmapdraw.getBitmap();
            smallMarker = Bitmap.createScaledBitmap(b, 80, 80, false);
            AnhXa();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            if (isLogin) {
                HideItem();
            }

            final Intent intent = getIntent();

            if (intent != null) {

                UserName = intent.getStringExtra(LoginActivity.USERNAME);
                Email = intent.getStringExtra(LoginActivity.EMAIL);
                profilePicUrl = intent.getStringExtra(LoginActivity.PIC_URL);

                //-----------------------------------------------------------------Thêm
                Gender = intent.getStringExtra(LoginActivity.GENDER);
                Dob = intent.getStringExtra(LoginActivity.DOB);
                //
                try {
                    if (Build.VERSION.SDK_INT > 9) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        URL fb_url = new URL(profilePicUrl);//small | noraml | large
                        HttpsURLConnection conn1 = (HttpsURLConnection) fb_url.openConnection();
                        HttpsURLConnection.setFollowRedirects(true);
                        conn1.setInstanceFollowRedirects(true);
                        Bitmap fb_img = BitmapFactory.decodeStream(conn1.getInputStream());

                        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(fb_img, 100);

                        imgLogo = navigationView.getHeaderView(0).findViewById(R.id.imgLogo);
                        imgLogo.setImageBitmap(circularBitmap);

                        imgLogo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent1 = new Intent(MainActivity.this, UserInfoActivity.class);

                                //-----------------------------------------------------------------Thêm
                                intent1.putExtra(LoginActivity.USERNAME, UserName);
                                intent1.putExtra(LoginActivity.EMAIL, Email);
                                intent1.putExtra(LoginActivity.DOB, Dob);
                                intent1.putExtra(LoginActivity.GENDER, Gender);
                                intent1.putExtra(LoginActivity.PIC_URL, profilePicUrl);
                                //
                                startActivity(intent1);
                            }
                        });
                    }
                }catch (Exception ex) {
                    ex.printStackTrace();
                }

            }


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();


            txtUsername = navigationView.getHeaderView(0).findViewById(R.id.txtUserName);
            txtUsername.setText(UserName);

            txtEmail = navigationView.getHeaderView(0).findViewById(R.id.txtEmail);
            txtEmail.setText(Email);

            ReciveMess();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fab.startAnimation(controlFabForward);
                    fabTimDuong.startAnimation(moveTop);
                    fabTraCuu.startAnimation(moveLeft);
                    fabToaDo.startAnimation(moveCrossover);
                    if(checkShowHide == true) {
                        fabTimDuong.show();
                        fabToaDo.show();
                        fabTraCuu.show();
                        checkShowHide = false;
                    }else {
                        fab.startAnimation(controlFabBackward);
                        fabTimDuong.hide();
                        fabToaDo.hide();
                        fabTraCuu.hide();
                        checkShowHide = true;
                    }

                }
            });
            fabToaDo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nameLocation = "";
                    LatLng home = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
                    CameraUpdate cameraHome = CameraUpdateFactory.newLatLngZoom(home, 16);
                    map.animateCamera(cameraHome);
                    Log.d("cur", "location");
                }
            });
            fabTimDuong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mode = 0;
                    ShowDialogTimDuong();
                }
            });
            fabTraCuu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i=0;i<arrayLoadIM.size();i++){
                        if (arrayLoadIM.get(i).isCancelled()==false){
                            arrayLoadIM.get(i).cancel(true);
                        }
                        if (i==arrayLoadIM.size()-1){
                            arrayLoadIM.clear();
                        }
                    }
                    for (int i=0;i<arrayReadJS.size();i++){
                        if (arrayReadJS.get(i).isCancelled()==false){
                            arrayReadJS.get(i).cancel(true);
                        }
                        if (i==arrayReadJS.size()-1){
                            arrayReadJS.clear();
                        }
                    }


                    Intent intent = new Intent(MainActivity.this, TraCuuActivity.class);
                    startActivity(intent);
                }
            });



        }

        static public final int REQUEST_LOCATION = 1;
        static public boolean PERMISSION_ACCESS_LOCATION = true;
        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            if (requestCode == REQUEST_LOCATION) {
                if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PERMISSION_ACCESS_LOCATION = true;
                } else {
                    PERMISSION_ACCESS_LOCATION = false;
                }
            }
        }

        @TargetApi(Build.VERSION_CODES.M)
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng near = new LatLng(10.7603902, 106.6816352);
            MyInfoWindownAdapter myInfoWindownAdapter=new MyInfoWindownAdapter(this);
            map = googleMap;

            map.setInfoWindowAdapter(myInfoWindownAdapter);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(near, 16);


            map.moveCamera(cameraUpdate);

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED){
                Log.d("print ", "no");
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }


            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.d("print ", "no2");
                // Should we show an explanation?
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            }

            if (!PERMISSION_ACCESS_LOCATION) {
                return;
            }
            map.setMyLocationEnabled(true);

            map.setOnMyLocationButtonClickListener(this);
            map.setOnMyLocationClickListener(this);
            map.setOnCameraIdleListener(this);
            map.setOnInfoWindowClickListener(this);

            Location a = map.getMyLocation();
            LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            Criteria mCriteria = new Criteria();
            String bestProvider = String.valueOf(manager.getBestProvider(mCriteria, true));


            Location mLocation = manager.getLastKnownLocation(bestProvider);
            if (mLocation != null) {
                final double currentLatitude = mLocation.getLatitude();
                final double currentLongitude = mLocation.getLongitude();
                LatLng loc1 = new LatLng(currentLatitude, currentLongitude);


                CameraUpdate cameraHome = CameraUpdateFactory.newLatLngZoom(loc1, 16);
                map.moveCamera(cameraHome);

            }
        }


        private void HideItem() {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.navLogin).setVisible(false);
            nav_Menu.findItem(R.id.navLogout).setVisible(true);
        }

        //ánh xạ
        public void AnhXa(){
            fab = (FloatingActionButton) findViewById(R.id.fab);
            fabTimDuong = (FloatingActionButton) findViewById(R.id.fabTimDuong);
            fabTraCuu = (FloatingActionButton) findViewById(R.id.fabTraCuu);
            fabToaDo = (FloatingActionButton) findViewById(R.id.fabToaDo);
            fabTimDuong.hide();
            fabToaDo.hide();
            fabTraCuu.hide();

            controlFabForward = AnimationUtils.loadAnimation(this, R.anim.controlfab_forward);
            controlFabBackward = AnimationUtils.loadAnimation(this,R.anim.controlfab_backward);
            moveTop = AnimationUtils.loadAnimation(this, R.anim.movetop);
            moveLeft = AnimationUtils.loadAnimation(this,R.anim.moveleft);
            moveCrossover = AnimationUtils.loadAnimation(this, R.anim.movecrossover);
        }

        @Override
        public void onBackPressed() {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_search, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            if(id == R.id.mnSearch) {
                editPlace = 2;
                openAutocompleteActivity();
            }

            //noinspection SimplifiableIfStatement
    //        if (id == R.id.action_settings) {
    //            return true;
    //        }

            return super.onOptionsItemSelected(item);
        }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        // chon item thanh cong cu
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();
            Intent intent = null;
            if (id == R.id.navReport) {
                intent = new Intent(this, GopYSanPhamActivity.class);
                startActivity(intent);
            }
            else if(id == R.id.navAbout){
                intent = new Intent(this, ThongTinUngDungActivity.class);
                startActivity(intent);
            }
            else if(id == R.id.navSearch){

                for (int i=0;i<arrayLoadIM.size();i++){
                    if (arrayLoadIM.get(i).isCancelled()==false){
                        arrayLoadIM.get(i).cancel(true);
                    }
                    if (i==arrayLoadIM.size()-1){
                        arrayLoadIM.clear();
                    }
                }
                for (int i=0;i<arrayReadJS.size();i++){
                    if (arrayReadJS.get(i).isCancelled()==false){
                        arrayReadJS.get(i).cancel(true);
                    }
                    if (i==arrayReadJS.size()-1){
                        arrayReadJS.clear();
                    }
                }


                intent = new Intent(this, TraCuuActivity.class);
                startActivity(intent);



            }
            else if (id == R.id.navSetting) {
                ShowDialogSetting();
            }
    //        else if (id == R.id.navTheme) {
    //            ShowDialogChonChuDe();
    //        }
            else if (id == R.id.navLogin){
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
            else if (id == R.id.navLogout) {
                disconnectFromFacebook();
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        // log out
        private void disconnectFromFacebook() {
            LoginManager.getInstance().logOut();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.navLogin).setVisible(true);
            nav_Menu.findItem(R.id.navLogout).setVisible(false);
            UserName = "";
            Email = "";
            profilePicUrl = "";
            isLogin = false;
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        @Override
        public void onCameraIdle() {
            CameraPosition a = map.getCameraPosition();
            LatLng vt = a.target;
            ReadJson readJson=new ReadJson();
            readJson.execute(createUrl(vt));
            arrayReadJS.add(readJson);
            Log.d("go", "go");
        }

        @Override
        public void onInfoWindowClick(Marker marker) {
            ShowDialogTransportSelection(marker);

        }

        @Override
        public boolean onMyLocationButtonClick() {
            return false;
        }

        @Override
        public void onMyLocationClick(@NonNull Location location) {

        }

        public class ReadJson extends AsyncTask<String, Void, String>{
            @Override
            protected String doInBackground(String... strings) {

                StringBuilder content = new StringBuilder();

                try {
                    URL url = new URL(strings[0]);
                    InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        content.append(line);
                    }
                    bufferedReader.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return content.toString();
            }

            @Override
            protected void onPostExecute(String s) {
                Log.d("here", "onPostExecute");
                super.onPostExecute(s);
                try {





                    try {
                        //Load File
                        Context mContext=getApplicationContext();
                        BufferedReader jsonReader = new BufferedReader(new InputStreamReader(mContext.getResources().openRawResource(R.raw.location)));
                        StringBuilder jsonBuilder = new StringBuilder();
                        for (String line = null; (line = jsonReader.readLine()) != null;) {
                            jsonBuilder.append(line).append("\n");
                        }

                        //Parse Json
                        JSONTokener tokener = new JSONTokener(jsonBuilder.toString());
                        JSONArray jsonArray = new JSONArray(tokener);
                        Log.d("jsonFile", jsonArray.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Log.d("string name", object.getString("id"));
                            Log.d("name location", nameLocation);
                            if (object.getString("id").equals(nameLocation) == false) {
                                continue;
                            }
                            Double tung = object.getDouble("locationX");
                            Double hoanh = object.getDouble("locationY");
                            Log.d("tung", tung.toString());
                            Log.d("hoanh", hoanh.toString());
                            LatLng near = new LatLng(tung, hoanh);
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(near);
                            String cover=object.getString("name");
                            markerOptions.title(object.getString("name"));
                            markerOptions.snippet(cover);
                            Marker a = map.addMarker(markerOptions);
                            Log.d("go", "here");
                            //move Map
                            final double currentLatitude = tung;
                            final double currentLongitude = hoanh;
                            LatLng loc1 = new LatLng(currentLatitude, currentLongitude);
                            Log.d("go", "camera");

                            CameraUpdate cameraHome = CameraUpdateFactory.newLatLngZoom(loc1, 16);
                            map.moveCamera(cameraHome);
                        }

                    } catch (FileNotFoundException e) {
                        Log.e("jsonFile", "file not found");
                    } catch (IOException e) {
                        Log.e("jsonFile", "ioerror");
                    } catch (JSONException e) {
                        Log.e("jsonFile", "error while parsing json");
                    }



                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    Log.d("json", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        JSONObject toado = object.getJSONObject("geometry");
                        String name = object.getString("name");
                        String diachi=object.getString("vicinity");

                        JSONObject open=object.getJSONObject("opening_hours");
                        String op=open.getString("open_now");
                        String rating=object.getString("rating");

                        String cover=diachi+"/"+op+"/"+rating;

                        JSONObject location = toado.getJSONObject("location");
//                        JSONArray arPhcarTravel=object.getJSONArray("phcarTravels");
//                        String phcarTravel_reference= arPhcarTravel.join("phcarTravel_reference");
//                        int vt=phcarTravel_reference.indexOf("phcarTravel_reference");
//                        phcarTravel_reference=phcarTravel_reference.substring(vt+18);
//                        vt=phcarTravel_reference.indexOf("\"");
//                        phcarTravel_reference=phcarTravel_reference.substring(0,vt);
                        Double tung = location.getDouble("lat");
                        Double hoanh = location.getDouble("lng");
                        LatLng near = new LatLng(tung, hoanh);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(near);
                        markerOptions.title(name);
                        markerOptions.snippet(cover);
                        String url="https://maps.googleapis.com/maps/api/place/phcarTravel?maxwidth=400&sensor=false&key=" + GOOGLE_API_KEY;
                        Log.d("url 2", url);
                        Marker a = map.addMarker(markerOptions);
                        //Bitmap aa=null;


                        LoadImage loadImage=new LoadImage();



                        loadImage.execute(url,a);


                        arrayLoadIM.add(loadImage);




                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        }
        }
        public String createUrl(LatLng vitri) {
            String url = "https://maps.googleapis.com/maps/api/place/search/json?location=" + vitri.latitude + "," + vitri.longitude + "&radius=500&types=convenience_store&keyword="+namePlace+"&key=" + GOOGLE_API_KEY;
            Log.d("url", url);
            return url;
        }
        public class LoadImage extends AsyncTask<Object,Void,Bitmap>{

            Bitmap bitmap;
            Marker a;
            @Override
            protected Bitmap doInBackground(Object... objects) {
                a= (Marker) objects[1];
                try {
                    URL url=new URL((String) objects[0]);
                    InputStream inputStream=url.openConnection().getInputStream();
                    bitmap= BitmapFactory.decodeStream(inputStream);
                } catch (MalformedURLException e) {
                    return null;
                } catch (IOException e) {
                    return null;
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                a.setTag(bitmap);
            }


        }
        //nhan mess tu tra cuu
        public String ReciveMess() {
            String mess = "";
            Intent intent = getIntent();
            if (intent != null) {
                Bundle bundle = intent.getBundleExtra("BUNDLE");
                if (bundle != null) {
                    mess = bundle.getString("MESS");
                    namePlace=mess;
                    nameLocation=mess;

                }

            }

            return mess;
        }

        private void ShowDialogTimDuong() {
            Log.d("show Dialog", "tim duong");
            findDialog = new Dialog(this);
            findDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            findDialog.setContentView(R.layout.activity_timduong);
            findDialog.setCanceledOnTouchOutside(false);
            Button find = findDialog.findViewById(R.id.findBtn);
            Button exit = findDialog.findViewById(R.id.exitBtn);
            from = findDialog.findViewById(R.id.fromTxt);
            to = findDialog.findViewById(R.id.toTxt);


            from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editPlace = 0;
                    Log.d("from", "opened");
                    openAutocompleteActivity();
                }
            });

            to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editPlace = 1;
                    openAutocompleteActivity();
                }
            });

            find.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendRequest();
                }
            });
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findDialog.dismiss();
                }
            });
            findDialog.show();
        }

        private void openAutocompleteActivity() {
            Log.d("autocomplete", "opened");
            try {
                AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setCountry("VN").build();
                Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                        .setFilter(autocompleteFilter)
                        .build(this);

                Log.d("asdasd", "ble ble");
                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
            }
            catch (GooglePlayServicesRepairableException e) {
                Log.d("error", e.toString());
                //User doesn't have Google Play Services
                GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),0).show();
            } catch (GooglePlayServicesNotAvailableException e) {
                Log.d("error", e.toString());
                // Indicates that Google Play Services is not available and the problem is not easily
                // resolvable.
                String message = "Google Play Services is not available: " +
                        GoogleApiAvailability.getInstance().getErrorString(e.errorCode);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
        private void sendRequest2(String from,String to,String mode){

            try {
                new FindDirection(this, from,to, mode).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        private void sendRequest() {
            Log.d("send Request", "done");
            String fromTxt = from.getText().toString();
            String toTxt = to.getText().toString();
            String modeTxt = "driving"; //walking , driving

            if (mode == 1) {
                modeTxt = "walking";
            }

            if (fromTxt.isEmpty()) {
                Toast.makeText(this, "Nhập địa điểm xuất phát", Toast.LENGTH_SHORT).show();
                return;
            }
            if (toTxt.isEmpty()) {
                Toast.makeText(this, "Nhập địa điểm đến", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                findDialog.dismiss();
                new FindDirection(this, fromTxt, toTxt, modeTxt).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        public void onDirectionFinderStart() {
            progressDialog = ProgressDialog.show(this, "Please wait...","Finding ...", true);

            if (originMarker != null) {
                for (Marker marker : originMarker) {
                    marker.remove();
                }
            }

            if (destinationMarker != null) {
                for (Marker marker : destinationMarker) {
                    marker.remove();
                }
            }

            if (polylinePaths != null) {
                for (Polyline polyline:polylinePaths ) {
                    polyline.remove();
                }
            }
        }

        // @Override
        public void onDirectionFinderSuccess(List<Router> routes) {
            progressDialog.dismiss();
            polylinePaths = new ArrayList<>();
            originMarker = new ArrayList<>();
            destinationMarker = new ArrayList<>();

            for (Router route : routes) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 15));

                BitmapDescriptor current;
                List<PatternItem> polyLine;

                if (mode == 0) {
                    current = BitmapDescriptorFactory.fromBitmap(smallCar);
                    polyLine = null;
                }
                else {
                    current = BitmapDescriptorFactory.fromBitmap(smallHuman);
                    polyLine = Arrays.<PatternItem>asList(new Dot());
                }
                Marker end;
                originMarker.add(map.addMarker(new MarkerOptions()
                        .icon(current)
                        .title("")
                        .position(route.startLocation)));
                destinationMarker.add(end=map.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                        .title("")
                        .position(route.endLocation)));
                end.setTag("Đi Đến Đây");

                PolylineOptions polylineOptions = new PolylineOptions().
                        geodesic(true).
                        color(Color.argb(255, 52, 152, 219)).
                        pattern(polyLine).
                        width(20);


                for (int i = 0; i < route.points.size(); i++)
                    polylineOptions.add(route.points.get(i));

                polylinePaths.add(map.addPolyline(polylineOptions));
            }
            mode = 0;
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            // Check that the result was from the autocomplete widget.
            if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
                if (resultCode == RESULT_OK) {
                    // Get the user's selected place from the Intent.
                    Place place = PlaceAutocomplete.getPlace(this, data);


                    if (editPlace == 0) {
                        from.setText(place.getName() + ", " + place.getAddress());
                    }
                    else if (editPlace == 1) {
                        to.setText(place.getName() + ", " + place.getAddress());
                    }
                    else if (editPlace == 2) {

                        LatLng near = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);

                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(near, 17);
                        map.moveCamera(cameraUpdate);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(place.getLatLng());
                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                        markerOptions.title("");
                        Marker a=map.addMarker(markerOptions);

                    }
                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                    Status status = PlaceAutocomplete.getStatus(this, data);

                } else if (resultCode == RESULT_CANCELED) {
                    // Indicates that the activity closed before a selection was made. For example if
                    // the user pressed the back button.
                }
            }
        }

        public void swapOnClick(View view) {
            String tmp = from.getText().toString();
            from.setText(to.getText().toString());
            to.setText(tmp);
        }

        public void modeOnClick(View view) {

            RadioButton walk = (RadioButton) typeDialog.findViewById(R.id.rdbWalk);
            RadioButton drive = (RadioButton) typeDialog.findViewById(R.id.rdbCar);

            if (view.getId() == R.id.rdbWalk) {
                drive.setChecked(false);
                mode = 1;

            }
            if (view.getId() == R.id.rdbCar) {
                walk.setChecked(false);
                mode = 0;
            }
        }

        public void typeOnClick(View view) {
            RadioButton normalBtn = (RadioButton) typeDialog.findViewById(R.id.rdbNormal);
            RadioButton hybridBtn = (RadioButton) typeDialog.findViewById(R.id.rdbHybrid);
            RadioButton terrainBtn = (RadioButton) typeDialog.findViewById(R.id.rdbTerrain);

            if (view.getId() == R.id.rdbNormal) {
                hybridBtn.setChecked(false);
                terrainBtn.setChecked(false);
                type = 0;
            }
            else if (view.getId() == R.id.rdbHybrid) {
                normalBtn.setChecked(false);
                terrainBtn.setChecked(false);
                type = 1;
            }
            else if (view.getId() == R.id.rdbTerrain) {
                normalBtn.setChecked(false);
                hybridBtn.setChecked(false);
                type = 2;
            }
            if (type == 0) {
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
            else if (type == 1) {
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
            else if (type == 2) {
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            }
        }

        private void ShowDialogSetting() {
            // Chon loai ban do
            typeDialog = new Dialog(this);
            typeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            typeDialog.setContentView(R.layout.activity_maptype);
            typeDialog.setCanceledOnTouchOutside(true);

            RadioButton normalBtn = (RadioButton) typeDialog.findViewById(R.id.rdbNormal);
            RadioButton hybridBtn = (RadioButton) typeDialog.findViewById(R.id.rdbHybrid);
            RadioButton terrainBtn = (RadioButton) typeDialog.findViewById(R.id.rdbTerrain);
            if (type == 0) {
                normalBtn.setChecked(true);
            }
            else if (type == 1) {
                hybridBtn.setChecked(true);
            }
            else {
                terrainBtn.setChecked(true);
            }
            // Chon theme

            Switch themeBtn = (Switch) typeDialog.findViewById(R.id.themeSwitch);

            if (theme == 1) {
                themeBtn.setChecked(true);
            }

            typeDialog.show();
        }

        public void themeOnClick(View view) {
            Switch themeBtn = (Switch) typeDialog.findViewById(R.id.themeSwitch);

            if (theme == 0) {
                theme = 1;
                themeBtn.setChecked(true);
                map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.dark_style_json));
            }
            else {
                theme = 0;
                themeBtn.setChecked(false);
                map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.light_style_json));
            }
        }
        public Marker MARKER = null;
        private void ShowDialogTransportSelection(final Marker marker2) {
            oDialog = new Dialog(this);
            oDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            oDialog.setContentView(R.layout.activity_maptype);
            oDialog.setCanceledOnTouchOutside(true);
            MARKER = marker2;
            oDialog.show();
        }
        // TODO: debugging
//        public void transportOnClick(View view) {
//            oDialog = new Dialog(this);
//            oDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            oDialog.setContentView(R.layout.activity_maptype);
//            oDialog.setCanceledOnTouchOutside(true);
//            final RadioButton walking = (RadioButton) oDialog.findViewById(R.id.walking);
//            final RadioButton carTravel = (RadioButton) oDialog.findViewById(R.id.carTravel);
//            if (view.getId() == R.id.walking) {
//                    carTravel.setChecked(false);
//                    walking.setChecked(true);
//                    Log.d("transport", "true");
//                    Location a=map.getMyLocation();
//                    Double x=a.getLatitude();
//                    Double y=a.getLongitude();
//                    LatLng home = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
//                    CameraUpdate cameraHome = CameraUpdateFactory.newLatLngZoom(home, 16);
//                    map.animateCamera(cameraHome);
//                    String from=x.toString()+","+y.toString();
//                    Double x2=MARKER.getPosition().latitude;
//                    Double y2=MARKER.getPosition().longitude;
//                    String to=x2.toString()+","+y2.toString();
//                    mode = 1;
//                    sendRequest2(from,to,"walking");
//            } else {
//                    walking.setChecked(false);
//                    carTravel.setChecked(true);
//                    Location a=map.getMyLocation();
//                    Double x=a.getLatitude();
//                    Double y=a.getLongitude();
//                    LatLng home = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
//                    CameraUpdate cameraHome = CameraUpdateFactory.newLatLngZoom(home, 16);
//                    map.animateCamera(cameraHome);
//                    String from=x.toString()+","+y.toString();
//                    Double x2=MARKER.getPosition().latitude;
//                    Double y2=MARKER.getPosition().longitude;
//                    String to=x2.toString()+","+y2.toString();
//                    mode = 0;
//                    sendRequest2(from,to,"driving");
//            };
//        }

}
