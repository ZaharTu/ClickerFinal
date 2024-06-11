package com.example.clicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.clicker.databinding.FragmentMenuBinding;

public class MenuFragment extends Fragment {
    private FragmentMenuBinding binding;
    private AllResRepository repository;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentMenuBinding.inflate(getLayoutInflater());
        NavController controller = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView);
        repository=AllResRepository.getInstance(getContext());
        binding.btnHelp.setOnClickListener(v -> {
            controller.navigate(R.id.action_menuFragment_to_helpFragment);
        });
        binding.debug.setOnClickListener(v -> {
            repository.setBalance(1000000000);
            repository.setPotato(500);
        });
        binding.seekBarAllMusic.setProgress((int)(repository.getVolumeAll()*100));
        binding.VolumeAllPercent.setText((int) (repository.getVolumeAll()*100)+"%");
        binding.seekBarAllMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                repository.setVolumeAll(((float) progress)/100);
                binding.VolumeAllPercent.setText(progress+"%");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        binding.seekBarBackMusic.setProgress((int)(repository.getVolumeBack()*100));
        binding.VolumeBackPercent.setText((int) (repository.getVolumeBack()*100)+"%");
        binding.seekBarBackMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                repository.setVolumeBack(((float) progress)/100);
                binding.VolumeBackPercent.setText(progress+"%");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        return binding.getRoot();
    }
}
