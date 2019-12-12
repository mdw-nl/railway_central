package nl.medicaldataworks.railway.central.web.controller;

import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.DataSource;
import nl.medicaldataworks.railway.central.repository.DataSourceRepository;
import nl.medicaldataworks.railway.central.util.PaginationUtil;
import nl.medicaldataworks.railway.central.util.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@Transactional
public class DataSourceController {
    private final DataSourceRepository dataSourceRepository;

    public DataSourceController(DataSourceRepository dataSourceRepository) {
        this.dataSourceRepository = dataSourceRepository;
    }

    @GetMapping("datasources/{id}")
    public ResponseEntity<DataSource> getDataSource(@PathVariable Long id) {
        log.debug("REST request to get datasources : {}", id);
        Optional<DataSource> dataSource = dataSourceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dataSource);
    }

    @GetMapping("datasources")
    public ResponseEntity<List<DataSource>> getAllDataSources(Pageable pageable) {
        log.debug("Getting datasources");
        Page<DataSource> page = dataSourceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("datasources")
    public ResponseEntity<DataSource> createDataSource(@RequestBody DataSource dataSource) throws URISyntaxException {
        log.debug("REST request to save Datasource : {}", dataSource);
        if (dataSource.getId() != null) {
            throw new IllegalArgumentException("A new datasource cannot already have an ID");
        }
        DataSource result = dataSourceRepository.save(dataSource);
        return ResponseEntity.created(new URI("/apidatasources/" + result.getId()))
                .body(result);
    }

    @PutMapping("datasources")
    public ResponseEntity<DataSource> updateDataSource(@RequestBody DataSource dataSource) throws URISyntaxException {
        log.debug("REST request to update datasource : {}", dataSource);
        if (dataSource.getId() == null) {
            throw new IllegalArgumentException("Invalid id");
        }
        DataSource result = dataSourceRepository.save(dataSource);
        return ResponseEntity.ok()
                .body(result);
    }

    @DeleteMapping("datasources/{id}")
    public ResponseEntity<Void> deleteDataSource(@PathVariable Long id) {
        log.debug("REST request to delete datasource : {}", id);
        dataSourceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}