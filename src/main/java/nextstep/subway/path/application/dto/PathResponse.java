package nextstep.subway.path.application.dto;

import nextstep.subway.station.application.dto.StationResponse;

import java.util.List;

public class PathResponse {
    private List<StationResponse> stations;
    private Long distance;

    public PathResponse(List<StationResponse> stations,
                        Long distance) {
        this.stations = stations;
        this.distance = distance;
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public Long getDistance() {
        return distance;
    }
}
