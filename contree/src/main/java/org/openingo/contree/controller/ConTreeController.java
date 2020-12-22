package org.openingo.contree.controller;

import org.openingo.contree.service.IConTreeNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ConTreeController
 *
 * @author Qicz
 */
@RestController
public class ConTreeController {

    @Autowired
    IConTreeNodeService conTreeNodeService;

    @GetMapping("/tree")
    public Object tree() {
        return this.conTreeNodeService.list();
    }
}
