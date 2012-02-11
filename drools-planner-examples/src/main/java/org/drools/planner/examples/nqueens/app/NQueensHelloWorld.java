/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.planner.examples.nqueens.app;

import java.util.List;

import org.drools.planner.config.SolverFactory;
import org.drools.planner.config.XmlSolverFactory;
import org.drools.planner.core.Solver;
import org.drools.planner.examples.nqueens.domain.NQueens;
import org.drools.planner.examples.nqueens.domain.Queen;
import org.drools.planner.examples.nqueens.persistence.NQueensGenerator;

public class NQueensHelloWorld {

    public static void main(String[] args) {
        // Build the Solver
        SolverFactory solverFactory = new XmlSolverFactory(
                "/org/drools/planner/examples/nqueens/solver/nqueensSolverConfig.xml");
        Solver solver = solverFactory.buildSolver();

        // Load the problem
        NQueens unsolved8Queens = new NQueensGenerator().createNQueens(8);

        // Solve the problem
        solver.setPlanningProblem(unsolved8Queens);
        solver.solve();
        NQueens solved8Queens = (NQueens) solver.getBestSolution();

        // Display the result
        System.out.println("\nSolved 8 queens:\n" + toGridString(solved8Queens));
    }

    public static String toGridString(NQueens nQueens) {
        StringBuilder gridString = new StringBuilder();
        int n = nQueens.getN();
        List<Queen> queenList = nQueens.getQueenList();
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                Queen queen = queenList.get(column);
                if (queen.getColumn().getIndex() != column) {
                    throw new IllegalStateException("The queenList is not in the expected order.");
                }
                gridString.append(" ");
                if (queen.getRow() != null && queen.getRow().getIndex() == row) {
                    gridString.append("Q");
                } else {
                    gridString.append("_");
                }
            }
            gridString.append("\n");
        }
        return gridString.toString();
    }

}
