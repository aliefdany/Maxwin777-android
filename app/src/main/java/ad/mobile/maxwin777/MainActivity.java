package ad.mobile.maxwin777;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView angka1;
    private Thread thread;
    private Thread thread2;
    private Thread thread3;
    private Handler handler;
    private Button btStartStop;

    private ImageView slotImage;

    private SlotWheel slotWheel1;

    private SlotAdapter slotAdapter;
    private RecyclerView rvSlot;
    private RecyclerView rvSlot2;
    private RecyclerView rvSlot3;
    private SlotAdapter slotAdapter2;
    private SlotAdapter slotAdapter3;
    private Handler handler2;
    private Handler handler3;

    private int[] positions = {0,4,8};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btStartStop = (Button) findViewById(R.id.btStartStop);

        this.btStartStop.setOnClickListener(this);

        this.slotWheel1 =  new SlotWheel();

        this.rvSlot = (RecyclerView) findViewById(R.id.rvSlot);
        this.rvSlot2 = (RecyclerView) findViewById(R.id.rvSlot2);
        this.rvSlot3 = (RecyclerView) findViewById(R.id.rvSlot3);

        this.slotAdapter = new SlotAdapter(this);
        this.slotAdapter2 = new SlotAdapter(this);
        this.slotAdapter3 = new SlotAdapter(this);


        // memberitahu recyclerview pola layoutnya
        this.rvSlot.setLayoutManager(new LinearLayoutManager(this));
        this.rvSlot2.setLayoutManager(new LinearLayoutManager(this));
        this.rvSlot3.setLayoutManager(new LinearLayoutManager(this));


        // memasangkan adapter dengan recycler view
        this.rvSlot.setAdapter(this.slotAdapter);
        this.rvSlot2.setAdapter(this.slotAdapter2);
        this.rvSlot3.setAdapter(this.slotAdapter3);


        // kasih tahu kalau datanya berubah
        this.slotAdapter.notifyDataSetChanged();
        this.slotAdapter2.notifyDataSetChanged();
        this.slotAdapter3.notifyDataSetChanged();


        this.handler = new Handler(Looper.getMainLooper());
        this.handler2 = new Handler(Looper.getMainLooper());
        this.handler3 = new Handler(Looper.getMainLooper());

        this.thread = createThread(handler, rvSlot, positions,0);
        this.thread2 =  createThread(handler2, rvSlot2, positions,1);
        this.thread3 = createThread(handler3, rvSlot2, positions,2);


    }

    private Thread createThread(Handler handler, RecyclerView rv, int[] positions, final int position) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int index = position;
                    while(true) {
                        if(positions[index] == slotWheel1.slotDrawables.size() - 1) positions[index] = 0;
                        else positions[index]++;

                        handleSlotWheel(rv, positions[position]);

                        Thread.sleep(80);
                    }
                }catch (Exception e) {

                }
            }

            public void handleSlotWheel(RecyclerView rvSlot,int index) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if(index == 0) rvSlot.scrollToPosition(index);
                        else rvSlot.smoothScrollToPosition(index);
                    }
                });
            }
        });
    }

    public void handleThreadStart() {
        btStartStop.setText("Stop");


        this.thread = createThread(handler, this.rvSlot, positions,0);
        this.thread2 =  createThread(handler2, this.rvSlot2, positions,1);
        this.thread3 = createThread(handler3, this.rvSlot3, positions,2);


        this.thread.start();
        this.thread2.start();
        this.thread3.start();

    }

    public void handleThreadInterrupt() {
        if(this.thread.isAlive() && this.thread2.isAlive() && this.thread3.isAlive()) {
            btStartStop.setText("Start");
            this.thread.interrupt();
        } else if(!this.thread.isAlive() && this.thread2.isAlive() && this.thread3.isAlive()) {
            btStartStop.setText("Stop");
            this.thread2.interrupt();
        } else if(!this.thread.isAlive() && !this.thread2.isAlive() && this.thread3.isAlive()) {
            btStartStop.setText("Start");
            this.thread3.interrupt();
        }


    }

    @Override
    public void onClick(View view) {

        if(!this.thread.isAlive() && !this.thread2.isAlive() && !this.thread3.isAlive()) {
            handleThreadStart();
        } else {
            handleThreadInterrupt();
        }

    }
}