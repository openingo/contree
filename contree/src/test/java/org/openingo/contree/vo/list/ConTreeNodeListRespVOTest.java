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

package org.openingo.contree.vo.list;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.openingo.jdkits.collection.ListKit;
import org.openingo.jdkits.json.JacksonKit;

import java.util.List;

/**
 * ConTreeNodeListRespVOTest
 *
 * @author Qicz
 */
public class ConTreeNodeListRespVOTest {

    public static void main(String[] args) throws JsonProcessingException {
        List<ConTreeNodeListRespVO.ConTreeNodeRespVO> data = ListKit.emptyArrayList();

        // origin data
        int id = 1;
        for (int i = 0; i < 3; i++) {
            ConTreeNodeListRespVO.ConTreeNodeRespVO item = new ConTreeNodeListRespVO.ConTreeNodeRespVO();
            item.setNodeId(id);
            item.setNodeName("name"+id++);
            item.setRootNodeId(i);
            item.setNodeOrder(i);
            data.add(item);
            for (int j = 0; j < 3; j++) {
                item = new ConTreeNodeListRespVO.ConTreeNodeRespVO();
                item.setNodeId(id);
                item.setNodeName("name"+id++);
                item.setRootNodeId(i);
                item.setNodeOrder(j);
                data.add(item);
            }
        }

        ConTreeNodeListRespVO conTreeNodeListRespVO = new ConTreeNodeListRespVO();
        conTreeNodeListRespVO.setNodes(data);
        conTreeNodeListRespVO.redraw(true,0);

        System.out.println(JacksonKit.toJson(conTreeNodeListRespVO));
    }
}
