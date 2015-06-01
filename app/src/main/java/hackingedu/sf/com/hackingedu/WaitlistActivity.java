package hackingedu.sf.com.hackingedu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseUser;

import junit.framework.TestCase;

import org.w3c.dom.Text;


public class WaitlistActivity extends ActionBarActivity {
    ParseUser currentuser;
    TextView position;
    TextView numBehind;
    Typeface face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitlist);

        position = (TextView) findViewById(R.id.positionView);
        numBehind = (TextView) findViewById(R.id.numBehindView);

        face = Typeface.createFromAsset(getAssets(),"fonts/Michroma.ttf");
        position.setTypeface(face);
        numBehind.setTypeface(face);


        currentuser = ParseUser.getCurrentUser();
        if(currentuser != null)
        {
            Toast.makeText(getBaseContext(), "Current USER Exists!", Toast.LENGTH_LONG).show();
        }
        String pos = ((String) currentuser.get("position"));
        //SpannableString content = new SpannableString(pos);
        //content.setSpan(new UnderlineSpan(),0,content.length(),0);
        position.setText(pos);
        position.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_waitlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Continuing back to the login screen will you log out. Are you sure?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ParseUser.logOut();
                        currentuser = ParseUser.getCurrentUser();
                        if(currentuser == null) {
                            Intent intent = new Intent(WaitlistActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(), "Error logging you out, please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        //Intent setIntent = new Intent(Intent.ACTION_MAIN);
        //setIntent.addCategory(Intent.CATEGORY_HOME);
        //setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(setIntent);
    }
}
