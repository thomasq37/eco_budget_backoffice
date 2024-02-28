package fr.qui.eco_gestion_back_office.tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/tags")
@CrossOrigin(origins = "${app.cors.origin}")
public class TagController {

    @Autowired
    private TagService tagService;
    
    @GetMapping()
    public ResponseEntity<?> obtenirTags() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); 
        try {
			List<Tag> tags = tagService.obtenirTags(email);
	        return ResponseEntity.ok().body(tags);

		} catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason()); 
		}
    }

    // Endpoints REST pour gérer les opérations liées à Tag
}