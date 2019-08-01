package com.guru.ruc.orchestrator.rest;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.guru.ruc.orchestrator.service.AuditEventService;

/**
 * REST controller for getting the {@link AuditEvent}s.
 */
@RestController
@RequestMapping("/management/audits")
public class AuditResource {

	private final AuditEventService auditEventService;

	public AuditResource(AuditEventService auditEventService) {
		this.auditEventService = auditEventService;
	}

	/**
	 * {@code GET /audits} : get a page of {@link AuditEvent}s.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of {@link AuditEvent}s in body.
	 */
	@GetMapping
	public ResponseEntity<List<AuditEvent>> getAll(@RequestParam MultiValueMap<String, String> queryParams,
			UriComponentsBuilder uriBuilder, Pageable pageable) {
		Page<AuditEvent> page = auditEventService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
		return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
	}

	/**
	 * {@code GET  /audits} : get a page of {@link AuditEvent} between the
	 * {@code fromDate} and {@code toDate}.
	 *
	 * @param fromDate the start of the time period of {@link AuditEvent} to get.
	 * @param toDate   the end of the time period of {@link AuditEvent} to get.
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of {@link AuditEvent} in body.
	 */
	@GetMapping(params = { "fromDate", "toDate" })
	public ResponseEntity<List<AuditEvent>> getByDates(@RequestParam(value = "fromDate") LocalDate fromDate,
			@RequestParam(value = "toDate") LocalDate toDate, @RequestParam MultiValueMap<String, String> queryParams,
			UriComponentsBuilder uriBuilder, Pageable pageable) {

		Page<AuditEvent> page = auditEventService.findByDates(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant(),
				toDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant(), pageable);
		HttpHeaders headers = generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * {@code GET  /audits/:id} : get an {@link AuditEvent} by id.
	 *
	 * @param id the id of the entity to get.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
	 *         {@link AuditEvent} in body, or status {@code 404 (Not Found)}.
	 */
	@GetMapping("/{id:.+}")
	public ResponseEntity<AuditEvent> get(@PathVariable Long id) {
		return wrapOrNotFound(auditEventService.find(id));
	}

	static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
		return maybeResponse.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	private static final String HEADER_X_TOTAL_COUNT = "X-Total-Count";
	private static final String HEADER_LINK_FORMAT = "<{0}>; rel=\"{1}\"";

	/**
	 * Generate pagination headers for a Spring Data {@link Page} object.
	 * 
	 * @param uriBuilder The URI builder.
	 * @param page       The page.
	 * @param <T>        The type of object.
	 * @return http header.
	 */
	public static <T> HttpHeaders generatePaginationHttpHeaders(UriComponentsBuilder uriBuilder, Page<T> page) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HEADER_X_TOTAL_COUNT, Long.toString(page.getTotalElements()));
		int pageNumber = page.getNumber();
		int pageSize = page.getSize();
		StringBuilder link = new StringBuilder();
		if (pageNumber < page.getTotalPages() - 1) {
			link.append(prepareLink(uriBuilder, pageNumber + 1, pageSize, "next")).append(",");
		}
		if (pageNumber > 0) {
			link.append(prepareLink(uriBuilder, pageNumber - 1, pageSize, "prev")).append(",");
		}
		link.append(prepareLink(uriBuilder, page.getTotalPages() - 1, pageSize, "last")).append(",")
				.append(prepareLink(uriBuilder, 0, pageSize, "first"));
		headers.add(HttpHeaders.LINK, link.toString());
		return headers;
	}

	private static String prepareLink(UriComponentsBuilder uriBuilder, int pageNumber, int pageSize, String relType) {
		return MessageFormat.format(HEADER_LINK_FORMAT, preparePageUri(uriBuilder, pageNumber, pageSize), relType);
	}

	private static String preparePageUri(UriComponentsBuilder uriBuilder, int pageNumber, int pageSize) {
		return uriBuilder.replaceQueryParam("page", Integer.toString(pageNumber))
				.replaceQueryParam("size", Integer.toString(pageSize)).toUriString().replace(",", "%2C")
				.replace(";", "%3B");
	}
}
