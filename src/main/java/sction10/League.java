package sction10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class League<T extends Team>{
    private List<T> teams = new ArrayList<>();

    public void addTeam(T team){
        teams.add(team);
    }

    public void printSortList(){
        Collections.sort(teams);
    }
}
