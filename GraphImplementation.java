import java.util.List;
import java.util.ArrayList;

public class GraphImplementation implements Graph 
{
	private int[][] adjMatrix;

	//Constructor
	public GraphImplementation (int vertices)
	{
		adjMatrix = new int[vertices][vertices];	//Creates an adjacency matrix to represent graph.
	}

	//Creates a directed, unweighted edge between two vertices.
	public void addEdge(int v1, int v2)
	{
		adjMatrix[v1][v2] = 1;
	}

	//Returns an array of vertices that a certain vertex points to.
	public int[] neighbors(int vertex)
	{
		int size = 0;
		int[] neigh = new int[size];	//Array of neighbors with initial size 0.
		for (int i = 0; i < adjMatrix.length; i++)
		{
			if (adjMatrix[vertex][i] > 0)
			{
				//Grows the array by 1 space to fit in a neighbor.
				int[] temp = new int[size + 1];	
				for (int n = 0; n < neigh.length; n++)
				{
					temp[n] = neigh[n];		//Copies values from original neighbor array to temp array.
				}
				neigh = temp;

				neigh[size++] = i;	//Adds the new neighbor to the array and increments the size.
			}
		}
		return neigh;
	}

	//Returns an ordering of the vertices in a graph.
	public List<Integer> topologicalSort()
	{
		List<Integer> schedule = new ArrayList<Integer>();		//Holds the ordering.

		List<Integer> incident = new ArrayList<Integer>();		//Keeps track of the incident edges of each vertex.
		
		//Initializes the incident array.
		for (int p = 0; p < adjMatrix.length; p++)
		{
			incident.add(0);	//All values start at 0.
		}
		for (int i = 0; i < adjMatrix.length; i++)
		{
			for (int j = 0; j < adjMatrix.length; j++)
			{
				if (adjMatrix[j][i] > 0)
				{
					int newvalue = adjMatrix[j][i] + incident.get(i);
					incident.set(i, newvalue);		//Vertices get incremented column-wise through the matrix.
				} 
			}
		}

		//Develops the schedule.
		boolean zerofound;
		
		for (int h = 0; h < adjMatrix.length; h++)		//For every vertex, we run a linear search to find a 0 in the incident array.
		{
			zerofound = false;
			for (int g = 0; g < adjMatrix.length && !zerofound; g++)	//Linear search.
			{
				if (incident.get(g) == 0)
				{
					schedule.add(g);		//If find 0, add that vertex to the schedule, and set that vertex in incident array to -1 to get it out of the way.
					incident.set(g, -1);
					zerofound = true;		//Since a 0 was found, we are done searching.

					//Updates the incident array by decrementing the neighbors of that vertex.
					int[] gPointsTo = neighbors(g);
					for (int e = 0; e < gPointsTo.length; e++)
					{
						int newvalue = incident.get(gPointsTo[e]) - 1;
						incident.set(gPointsTo[e], newvalue);
					}
				}
				//If 0 isn't found after searching the entire incident array, we have an error (probably a cycle).
				else if (zerofound == false && g == adjMatrix.length-1)
				{
					System.out.println ("Error--Sort not possible.");
					System.out.println ("Partial schedule:" + schedule);
					return schedule;
				}
			}
		}
		
		System.out.println ("Schedule:" + schedule);
		return schedule;
	}
}
