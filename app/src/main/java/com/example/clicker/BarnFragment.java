package com.example.clicker;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.clicker.databinding.FragmentMainBarnBinding;

public class BarnFragment extends Fragment {
    private FragmentMainBarnBinding binding;
    private Barn barn;
    private Context context;
    private AllResRepository repository;
    private ViewModel viewModel;
    private MutableLiveData<int[]> LiveMarket;
    private MutableLiveData<int[]> LiveResearch;
    private LiveData<Integer> LivePotato;
    private LiveData<Float> LiveVolume;
    private String barnName;
    private float volume=0.5f;
    private MediaPlayer mediaPlayerAdd;
    private MediaPlayer mediaPlayerDecr;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=FragmentMainBarnBinding.inflate(getLayoutInflater());
        context=getContext();
        barnName=getResources().getString(R.string.Barn);
        viewModel=new ViewModel(context);
        LiveMarket=viewModel.getMarketLiveData();
        LiveVolume=viewModel.getVolumeLiveData();
        LivePotato=viewModel.getPotatoLiveData();
        LiveResearch=viewModel.getResearchLiveData();
        mediaPlayerAdd=MediaPlayer.create(context,R.raw.slave_incr);
        mediaPlayerAdd.setVolume(volume,volume);
        mediaPlayerDecr=MediaPlayer.create(context,R.raw.slave_decr);
        mediaPlayerDecr.setVolume(volume,volume);
        repository=AllResRepository.getInstance(context);
        barn=new Barn(context);
        barn.setProgressBar(binding.BarnProgress);
        barn.setSlave(repository.getUsableNeighbor()[1]);
        binding.BarnSlaves.setText(""+barn.getSlave());
        binding.BarnPlusButton.setOnClickListener(v -> {
            if (repository.incrSlavesPos(1)){
                barn.IncrSlave();
                binding.BarnSlaves.setText(""+barn.getSlave());
                binding.BarnPotato.setText(repository.getPotato()+"/"+ barn.getMaxProgress()+"\uD83E\uDD54");
                mediaPlayerAdd.start();
            }
        });
        binding.BarnMinusButton.setOnClickListener(v -> {
            if (repository.decrSlavesPos(1)){
                barn.DincrSlave();
                binding.BarnSlaves.setText(""+barn.getSlave());
                binding.BarnPotato.setText(repository.getPotato()+"/"+ barn.getMaxProgress()+"\uD83E\uDD54");
                mediaPlayerDecr.start();
            }
        });
        binding.BarnPotato.setText(repository.getPotato()+"/"+ barn.getMaxProgress()+"\uD83E\uDD54");
        barn.IncrMaxProgress();
        binding.BarnProgress.setProgress(repository.getPotato());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(LiveResearch.getValue()!=null){
            if (LiveResearch.getValue()[2]==1) return binding.getRoot();
        }
        return inflater.inflate(R.layout.fragment_main_barn_b_and_w,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LiveResearch.observe(getViewLifecycleOwner(),research->{
            if (research[3]==1){
                barn.setResearched_barn_2(true);
            }
        });
        LivePotato.observe(getViewLifecycleOwner(),potato->{
            barn.IncrProgress(potato);
            binding.BarnPotato.setText(potato+"/"+ barn.getMaxProgress()+"\uD83E\uDD54");
        });
        LiveMarket.observe(getViewLifecycleOwner(),market->{
            barn.IncrMaxProgress();
            binding.BarnProgress.setMax(barn.getMaxProgress());
            binding.BarnPotato.setText(repository.getPotato()+"/"+barn.getMaxProgress()+"\uD83E\uDD54");
            binding.BarnName.setText(barnName+" УР "+market[6]);
        });
        LiveVolume.observe(getViewLifecycleOwner(),volume->{
            mediaPlayerAdd.setVolume(volume,volume);
            mediaPlayerDecr.setVolume(volume,volume);
        });
    }
}
