package com.travis.voxera;

import android.app.Activity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    private final int bg = Color.rgb(16,13,24);
    private final int panel = Color.rgb(35,28,48);
    private final int purple = Color.rgb(167,139,250);
    private LinearLayout root;
    private String nickname = "Tamu VOXERA";
    private boolean micOn = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);
        nickname = getSharedPreferences("voxera", MODE_PRIVATE).getString("nickname", "Tamu VOXERA");
        showHome();
    }

    private GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radius);
        return d;
    }

    private void base(String title, String subtitle) {
        ScrollView scroll = new ScrollView(this);
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(22, 26, 22, 28);
        root.setBackgroundColor(bg);
        scroll.setFillViewport(true);
        scroll.addView(root);
        setContentView(scroll);
        TextView brand = text("VOXERA", 30, purple, true);
        root.addView(brand);
        TextView heading = text(title, 23, Color.WHITE, true);
        LinearLayout.LayoutParams hp = new LinearLayout.LayoutParams(-1, -2);
        hp.topMargin = 22;
        root.addView(heading, hp);
        TextView sub = text(subtitle, 14, 0xFFBDB5CA, false);
        root.addView(sub);
    }

    private TextView text(String value, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }

    private void gap(int h) {
        Space s = new Space(this);
        root.addView(s, new LinearLayout.LayoutParams(1, h));
    }

    private void button(String label, Runnable action) {
        TextView b = text(label, 16, Color.WHITE, true);
        b.setGravity(Gravity.CENTER);
        b.setPadding(16, 17, 16, 17);
        b.setBackground(shape(panel, 20));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2);
        p.topMargin = 12;
        root.addView(b, p);
        b.setOnClickListener(v -> action.run());
    }

    private void showHome() {
        base("Temukan teman, mulai ngobrol", "Halo, " + nickname + " 👋");
        gap(18);
        button("🎙  ROOM SANTAI", () -> showRoom("ROOM SANTAI"));
        button("🎮  ROOM GAMING", () -> showRoom("ROOM GAMING"));
        button("🎵  ROOM MUSIC", () -> showRoom("ROOM MUSIC"));
        gap(18);
        button("🪙  Dompet • 0 Coin (Demo)", () -> showWallet());
        button("⭐  Agent Center (Demo)", () -> showAgent());
        button("👤  Ubah Nickname", () -> editNickname());
        gap(20);
        root.addView(text("VOXERA versi awal • Mode tampilan demo", 12, 0xFF8F879B, false));
    }

    private void editNickname() {
        EditText input = new EditText(this);
        input.setSingleLine(true);
        input.setText(nickname.equals("Tamu VOXERA") ? "" : nickname);
        input.setHint("Masukkan nickname");
        new android.app.AlertDialog.Builder(this)
            .setTitle("Nickname")
            .setView(input)
            .setNegativeButton("Batal", null)
            .setPositiveButton("Simpan", (d, w) -> {
                String n = input.getText().toString().trim();
                if (!n.isEmpty()) {
                    nickname = n;
                    getSharedPreferences("voxera", MODE_PRIVATE).edit().putString("nickname", n).apply();
                }
                showHome();
            }).show();
    }

    private void showRoom(String room) {
        micOn = false;
        base(room, "Ruang obrolan VOXERA");
        gap(20);
        TextView info = text("🎧  Kamu berada di " + room + "\n\nBelum ada anggota online.\nFitur suara langsung belum aktif di versi demo ini.", 17, Color.WHITE, false);
        info.setPadding(18, 22, 18, 22);
        info.setBackground(shape(panel, 20));
        root.addView(info);
        button("🎙  Nyalakan / Matikan Mic (Simulasi)", () -> {
            micOn = !micOn;
            Toast.makeText(this, micOn ? "Simulasi mic ON" : "Simulasi mic OFF", Toast.LENGTH_SHORT).show();
        });
        button("Kembali ke Beranda", () -> showHome());
    }

    private void showWallet() {
        base("Dompet VOXERA", "Saldo contoh untuk tampilan awal");
        gap(18);
        button("0 COIN", () -> Toast.makeText(this, "Top up belum tersedia", Toast.LENGTH_SHORT).show());
        button("Kirim Gift (Demo)", () -> Toast.makeText(this, "Gift belum memakai transaksi asli", Toast.LENGTH_SHORT).show());
        button("Kembali", () -> showHome());
    }

    private void showAgent() {
        base("Agent Center", "Pusat agen koin • Demo");
        gap(18);
        button("Saldo Agen: 0 COIN", () -> {});
        button("Fitur pendaftaran agen belum aktif", () -> {});
        button("Kembali", () -> showHome());
    }
}
