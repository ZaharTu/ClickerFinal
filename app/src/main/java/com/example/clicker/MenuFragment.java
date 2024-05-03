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
    private ViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentMenuBinding.inflate(getLayoutInflater());
        NavController controller = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView);
        viewModel=ViewModel.newInstance(getContext());
        binding.btnHelp.setOnClickListener(v -> {
            controller.navigate(R.id.action_menuFragment_to_helpFragment);
        });
        binding.seekBar.setProgress((int)(viewModel.getLiveDataResourses().getValue().getVolume()*100));
        binding.txtVolume.setText((int) (viewModel.getLiveDataResourses().getValue().getVolume()*100)+"%");
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewModel.setVolumeRes(((float) progress)/100);
                binding.txtVolume.setText(progress+"%");
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
