package nextstep.subway.favorite.application.dto;

import nextstep.subway.favorite.domain.Favorite;
import nextstep.subway.station.application.dto.StationResponseFactory;

import java.util.List;
import java.util.stream.Collectors;

public class FavoriteResponseFactory {

    public static List<FavoriteResponse> createFavoriteResponse(List<Favorite> favorites) {
        return favorites.stream()
                .map(FavoriteResponseFactory::createFavoriteResponse)
                .collect(Collectors.toList());
    }

    public static FavoriteResponse createFavoriteResponse(Favorite favorite) {
        return new FavoriteResponse(favorite.getId(),
                StationResponseFactory.createStationResponse(favorite.getSourceStation()),
                StationResponseFactory.createStationResponse(favorite.getTargetStation()));
    }
}
