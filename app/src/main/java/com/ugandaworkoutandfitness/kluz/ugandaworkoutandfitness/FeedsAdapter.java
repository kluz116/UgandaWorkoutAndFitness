package com.ugandaworkoutandfitness.kluz.ugandaworkoutandfitness;

        import android.content.Context;
        import android.content.Intent;
        import android.net.ParseException;
        import android.support.v7.widget.RecyclerView;
        import android.text.format.DateUtils;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;
        import com.bumptech.glide.load.engine.DiskCacheStrategy;


        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.List;


/**
 * Created by Allan Musembya on 14/11/16.
 */
public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.MyViewHolder> {

    private Context mContext;
    private List<FitnessModel> articleList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, author,date_created;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            author = (TextView) view.findViewById(R.id.author);
            date_created = (TextView) view.findViewById(R.id.date);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public FeedsAdapter(Context mContext, List<FitnessModel> articleList) {
        this.mContext = mContext;
        this.articleList = articleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feeds_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final FitnessModel article = articleList.get(position);
        holder.title.setText(article.getTitle());
        holder.author.setText(""+ article.getAuthor()+" ");

        try {
            long now = System.currentTimeMillis();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
            Date convertedDate = dateFormat.parse(article.getDateCreated());

            CharSequence relavetime1 = DateUtils.getRelativeTimeSpanString(
                    convertedDate.getTime(),
                    now,
                    DateUtils.HOUR_IN_MILLIS);
            holder.date_created.setText(relavetime1);


        }catch(ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }


        // loading article cover using Glide library
        Glide.with(mContext).load(article.getThumbnail()).centerCrop().crossFade().placeholder(R.drawable.images).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent in = new Intent(mContext,FitnessDetails.class);
                in.putExtra("article", article);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);

            }
        });

        holder.title.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent in = new Intent(mContext,FitnessDetails.class);
                in.putExtra("article", article);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);

            }
        });
    }


    @Override
    public int getItemCount() {
        return articleList.size();
    }
}

