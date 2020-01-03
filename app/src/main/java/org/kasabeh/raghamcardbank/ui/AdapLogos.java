package org.kasabeh.raghamcardbank.ui;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.utils.RaghamConst;

/**
 * Created by Saeed on 06/13/2018.
 */
public class AdapLogos extends RecyclerView.Adapter<AdapLogos.RowHolder> {

    private Context ctx;
    private int[] arrLogos;
    private String[] arrLogoNames;

    public AdapLogos(Context ctx){
        this.ctx = ctx;
        arrLogos = new int[27];
        arrLogoNames = new String[27];
        arrLogos[0] = R.drawable.bnk_def;arrLogoNames[0] = RaghamConst.BNK_DEF;
        arrLogos[1] = R.drawable.bnk_mellat;arrLogoNames[1] = RaghamConst.BNK_MELLAT;
        arrLogos[2] = R.drawable.bnk_melli;arrLogoNames[2] = RaghamConst.BNK_MELLI;
        arrLogos[3] = R.drawable.bnk_maskan;arrLogoNames[3] = RaghamConst.BNK_MASKAN;
        arrLogos[4] = R.drawable.bnk_saderat;arrLogoNames[4] = RaghamConst.BNK_SADERAT;
        arrLogos[5] = R.drawable.bnk_shahr;arrLogoNames[5] = RaghamConst.BNK_SHAHR;
        arrLogos[6] = R.drawable.bnk_sepah;arrLogoNames[6] = RaghamConst.BNK_SEPAH;
        arrLogos[7] = R.drawable.bnk_tejarat;arrLogoNames[7] = RaghamConst.BNK_TEJARAT;
        arrLogos[8] = R.drawable.bnk_keshavarzi;arrLogoNames[8] = RaghamConst.BNK_KESHAVARZI;
        arrLogos[9] = R.drawable.bnk_refah;arrLogoNames[9] = RaghamConst.BNK_REFAH;
        arrLogos[10] = R.drawable.bnk_parsian;arrLogoNames[10] = RaghamConst.BNK_PARSIAN;
        arrLogos[11] = R.drawable.bnk_passargad;arrLogoNames[11] = RaghamConst.BNK_PASSARGAD;
        arrLogos[12] = R.drawable.bnk_saman;arrLogoNames[12] = RaghamConst.BNK_SAMAN;
        arrLogos[13] = R.drawable.bnk_ansar;arrLogoNames[13] = RaghamConst.BNK_ANSAR;
        arrLogos[14] = R.drawable.bnk_ayandeh;arrLogoNames[14] = RaghamConst.BNK_AYANDEH;
        arrLogos[15] = R.drawable.bnk_dey;arrLogoNames[15] = RaghamConst.BNK_DEY;
        arrLogos[16] = R.drawable.bnk_eghtesad_novin;arrLogoNames[16] = RaghamConst.BNK_EGHTESAD_NOVIN;
        arrLogos[17] = R.drawable.bnk_gadeshgari;arrLogoNames[17] = RaghamConst.BNK_GADESHGARI;
        arrLogos[18] = R.drawable.bnk_gharzolhassaneh_mehreiran;arrLogoNames[18] = RaghamConst.BNK_GHARZOLHASSANEH_MEHREIRAN;
        arrLogos[19] = R.drawable.bnk_hekmat_iranian;arrLogoNames[19] = RaghamConst.BNK_HEKMAT_IRANIAN;
        arrLogos[20] = R.drawable.bnk_iran_venezuela;arrLogoNames[20] = RaghamConst.BNK_IRAN_VENEZUELA;
        arrLogos[21] = R.drawable.bnk_iranzamin;arrLogoNames[21] = RaghamConst.BNK_IRANZAMIN;
        arrLogos[22] = R.drawable.bnk_mehre_eghtesad;arrLogoNames[22] = RaghamConst.BNK_MEHRE_EGHTESAD;
        arrLogos[23] = R.drawable.bnk_post;arrLogoNames[23] = RaghamConst.BNK_POST;
        arrLogos[24] = R.drawable.bnk_sina;arrLogoNames[24] = RaghamConst.BNK_SINA;
        arrLogos[25] = R.drawable.bnk_sarmayeh;arrLogoNames[25] = RaghamConst.BNK_SARMAYEH;
        arrLogos[26] = R.drawable.bnk_sanat_madan;arrLogoNames[26] = RaghamConst.BNK_SANAT_MADAN;
    }

    @Override
    public RowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.row_logos, parent, false);
        return new RowHolder(v);
    }

    @Override
    public void onBindViewHolder(RowHolder holder, int position) {
        holder.imgLogo.setImageResource(arrLogos[position]);
    }

    @Override
    public int getItemCount() {
        return arrLogos.length;
    }

    public String getLogoFileName(int pos) {
        return arrLogoNames[pos];
    }

    public class RowHolder extends RecyclerView.ViewHolder {

        public ImageView imgLogo;

        public RowHolder(View rowView) {
            super(rowView);
            imgLogo = rowView.findViewById(R.id.imgBankLogo);
        }
    }
}
