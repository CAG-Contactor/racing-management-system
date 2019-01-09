import * as React from "react"

export class MyRaces extends React.Component {
  render() {
    const results: any[] = [
      {
        user: {
          name: 'Joe'
        },
        time: new Date(),
        splitTime: new Date(),
        result: 'FINISHED'
      },
      {
        user: {
          name: 'Gabbe'
        },
        time: new Date(),
        splitTime: new Date(),
        result: 'FINISHED'
      },
      {
        user: {
          name: 'Jobi'
        },
        time: new Date(),
        splitTime: new Date(),
        result: 'FINISHED'
      },
      {
        user: {
          name: 'Kaka'
        },
        time: new Date(),
        splitTime: new Date(),
        result: 'FINISHED'
      }
    ];
    return (
      <div className="container">
        <h1>Mina Lopp</h1>
          <table className="center table table-striped">
            <thead>
              <tr>
                <th>Namn</th>
                <th>Tid</th>
                <th>Mellantid</th>
                <th>Resultat</th>
              </tr>
            </thead>
            <tbody>
            {results.map((result, index) => {
              return (
                  <tr key={index}>
                    <td>{result.user.name}</td>
                    <td>{result.time.toUTCString()}</td>
                    <td>{result.splitTime.toUTCString()}</td>
                    <td>{result.result}</td>
                  </tr>
              );
            })}
            </tbody>
          </table>
      </div>
    )
  }
}

