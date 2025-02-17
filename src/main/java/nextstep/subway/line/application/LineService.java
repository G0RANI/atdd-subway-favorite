package nextstep.subway.line.application;

import nextstep.subway.line.application.dto.LineRequest;
import nextstep.subway.line.application.dto.LineResponse;
import nextstep.subway.line.application.dto.LineResponseFactory;
import nextstep.subway.line.application.dto.LineUpdateRequest;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineRepository;
import nextstep.subway.line.section.domain.Section;
import nextstep.subway.line.section.dto.SectionsUpdateRequest;
import nextstep.subway.station.domain.Station;
import nextstep.subway.station.domain.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    public LineService(LineRepository lineRepository,
                       StationRepository stationRepository) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
    }

    @Transactional
    public LineResponse saveLine(LineRequest lineRequest) {
        Line line = lineRepository.save(createLine(lineRequest));
        return LineResponseFactory.create(line);
    }

    public List<LineResponse> findAllLines() {
        return lineRepository.findAll().stream()
                .map(LineResponseFactory::create)
                .collect(Collectors.toList());
    }

    public LineResponse findLine(Long id) {
        Line line = getLine(id);
        return LineResponseFactory.create(line);
    }

    @Transactional
    public void updateLine(Long id,
                           LineUpdateRequest lineUpdateRequest) {
        Line line = getLine(id);
        line.update(lineUpdateRequest.getName(), lineUpdateRequest.getColor());
    }

    @Transactional
    public void deleteLine(Long id) {
        lineRepository.deleteById(id);
    }

    @Transactional
    public LineResponse addSection(Long id,
                                   SectionsUpdateRequest sectionsUpdateRequest) {
        Line line = getLine(id);
        line.addSection(createSection(sectionsUpdateRequest));
        lineRepository.save(line);
        return LineResponseFactory.create(line);
    }

    @Transactional
    public void deleteSection(Long id,
                              Long stationId) {
        Line line = getLine(id);
        line.deleteSection(getStation(stationId));
        lineRepository.save(line);
    }

    private Section createSection(SectionsUpdateRequest sectionsUpdateRequest) {
        return new Section(getStation(sectionsUpdateRequest.getUpStationId()),
                getStation(sectionsUpdateRequest.getDownStationId()),
                sectionsUpdateRequest.getDistance());
    }

    private Line createLine(LineRequest lineRequest) {
        return new Line(lineRequest.getName(),
                lineRequest.getColor(),
                getStation(lineRequest.getUpStationId()),
                getStation(lineRequest.getDownStationId()),
                lineRequest.getDistance());
    }

    private Line getLine(Long id) {
        return lineRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 지하철라인 정보를 찾지 못했습니다."));
    }

    private Station getStation(Long stationId) {
        return stationRepository.findById(stationId).orElseThrow(() -> new IllegalArgumentException("해당 지하철역 정보를 찾지 못했습니다."));
    }

}
