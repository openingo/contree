/*
 * MIT License
 *
 * Copyright (c) 2020 OpeningO Co.,Ltd.
 *
 *    https://openingo.org
 *    contactus(at)openingo.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.openingo.contree.biz.impl;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ConTreeNodeBizImplTest
 *
 * @author Qicz
 */
public class ConTreeNodeBizImplTest {

    @Data
    private static class Model {
        Integer id;
    }

    public static void p(Integer id) {
        List<Model> models = new ArrayList<>();
        for (int i = id; i < 5; i++) {
            Model model = new Model();
            model.setId(i);
            models.add(model);
        }
        System.out.println("id="+id+", origin="+models);
        models = models.stream().filter(m -> {
            System.out.println("invking.....");
            return Integer.valueOf(0).equals(id) || !id.equals(m.id);
        }).collect(Collectors.toList());
        System.out.println("id="+id+", filter="+models);
    }

    public static void main(String[] args) {
        Integer id = 0;
        p(id++);
        p(id);
    }
}
