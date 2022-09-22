package com.ideas2it.ems.dao.trainerdao;

import java.util.List;
import java.util.ArrayList;

import com.ideas2it.ems.model.trainermodel.Trainer;

public class TrainerDAO {
    static List<Trainer> trainerList = new ArrayList<Trainer>();

    public void addTrainer(Trainer trainer) {
        trainerList.add(trainer);
    }

    public List<Trainer> getTrainerList() {
        return trainerList;
    }

    public void updateTrainer(int trainerIndex, Trainer trainer) {
        trainerList.set(trainerIndex, trainer);
    }

    public void deleteTrainer(int trainerIndex) {
        trainerList.remove(trainerIndex);
    }
}