package id.example.bagasekaa.cekgadgetmu_2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import id.example.bagasekaa.cekgadgetmu_2.model.Order;
import id.example.bagasekaa.cekgadgetmu_2.model.Rincian;


public class CustomAdapter3 extends ArrayAdapter<Rincian> {

    private Activity context;
    List<Rincian> ListData;

    public CustomAdapter3(Activity context, List<Rincian> ListData) {
        super(context, R.layout.data_rincian, ListData);
        this.context = context;
        this.ListData = ListData;
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.data_rincian, null, true);

        TextView sparepart = (TextView) listViewItem.findViewById(R.id.sparepart);
        TextView harga = (TextView) listViewItem.findViewById(R.id.harga);
        TextView jumlah = (TextView) listViewItem.findViewById(R.id.jumlah);

        Rincian data = ListData.get(position);
        sparepart.setText(data.getSparepart());
        harga.setText(data.getHarga());
        jumlah.setText(data.getJumlah());

        return listViewItem;
    }
}