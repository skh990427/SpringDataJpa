package study.datajpa.repository;

public interface NestedClosedProjections {

    String getUsername(); //첫번째까지는 최적화가 됨

    TeamInfo getTeam(); //여기부터는 최적화가 안됨 다가져옴

    interface TeamInfo {
        String getName();
    }

}

//중첩구조(조인이 필요할 시) 엔티티 두개부터는 모든 필드를 전부 가져온다.