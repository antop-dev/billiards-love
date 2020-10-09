package org.antop.billiardslove.api.sample;

import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/sample")
class SampleApi {
    public static final String JSON = APPLICATION_JSON_VALUE;

    @RequestMapping(path = "/echo", method = {GET, POST})
    @ResponseBody
    String echo(@RequestParam String s) {
        return s;
    }

    @PostMapping(value = "/sum", consumes = JSON, produces = JSON)
    @ResponseBody
    SumResponse sum(@RequestBody SumRequest request) {
        return new SumResponse(request.getX() + request.getY());
    }

}
