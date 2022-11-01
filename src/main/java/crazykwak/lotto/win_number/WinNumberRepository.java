package crazykwak.lotto.win_number;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WinNumberRepository extends JpaRepository<WinNumber, Long> {

    @Query("select w from WinNumber w")
    Page<WinNumber> findLastIndex(Pageable pageable);

    WinNumber findByDrawNo(long drawNo);
}
