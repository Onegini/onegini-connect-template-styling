/*
 * Copyright 2019 Onegini B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.onegini.styling.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onegini.styling.service.ModelMapService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/render")
public class ShowTemplateController {

  @Resource
  private ModelMapService modelMapService;


  @GetMapping
  public String overview(final Model model) {
    model.addAllAttributes(modelMapService.loadAllTemplatePaths());
    return "templates";
  }

  @GetMapping(params = { "template" })
  public String main(final Model model, @RequestParam("template") final String template) {
    log.info("Showing template {}", template);

    model.addAllAttributes(modelMapService.loadTemplateModelMap(template));

    return template;
  }


}