package nextstep.subway.auth.application.dto;

import nextstep.subway.testhelper.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UserDetailTest {

    @Test
    @DisplayName("UserDetail 을 생성한다")
    void createUserDetail() {
        assertDoesNotThrow(() ->new UserDetail(MemberFixture.EMAIL, MemberFixture.PASSWORD,
                MemberFixture.AGE));
    }

    @Test
    @DisplayName("맞는 비밀번호인지 확인이 가능하다")
    void correctPassword() {
        UserDetail userDetail = new UserDetail(MemberFixture.EMAIL, MemberFixture.PASSWORD,
                MemberFixture.AGE);

        boolean actual = userDetail.correctPassword(MemberFixture.PASSWORD);
        boolean expected = true;
        assertThat(actual).isEqualTo(expected);
    }

}
